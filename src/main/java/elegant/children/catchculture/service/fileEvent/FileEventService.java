package elegant.children.catchculture.service.fileEvent;

import elegant.children.catchculture.entity.fileEvent.FileEvent;
import elegant.children.catchculture.repository.fileEvent.FileEventRepository;
import elegant.children.catchculture.service.GCS.GCSService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileEventService {

    private final FileEventRepository fileEventRepository;
    private final GCSService gcsService;


    @Transactional
    @EventListener
    public void handleFileEvent(final List<FileEvent> fileEventList) {
        fileEventRepository.saveAll(fileEventList);
    }

    @Transactional
    @Scheduled(fixedRate = 1000 * 60 * 60) //1시간마다
    public void deleteFileEvent() throws IOException {
        final List<FileEvent> result = fileEventRepository.findAll();
        if(result.isEmpty()) {
            return;
        }
        gcsService.deleteFileFromGCSByUrlList(result.stream().map(FileEvent::getFileName).collect(Collectors.toList()));
        fileEventRepository.deleteAll();
    }








}
