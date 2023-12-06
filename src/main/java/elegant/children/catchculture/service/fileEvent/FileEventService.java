package elegant.children.catchculture.service.fileEvent;

import elegant.children.catchculture.entity.fileEvent.FileEvent;
import elegant.children.catchculture.event.DeleteFileEvent;
import elegant.children.catchculture.repository.fileEvent.FileEventRepository;
import elegant.children.catchculture.service.GCS.GCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileEventService {

    private final FileEventRepository fileEventRepository;
    private final GCSService gcsService;


    @Transactional
    @EventListener
    public void handleFileEvent(final DeleteFileEvent deleteFileEvent) {
        log.info("handleFileEvent");
        fileEventRepository.saveAll(deleteFileEvent.getFileEventList());
    }

    @Transactional
    @Scheduled(fixedRate = 1000 * 60 * 60 * 3) //3시간마다
    public void deleteFileEvent() throws IOException {
        log.info("deleteFileEvent");
        final List<FileEvent> result = fileEventRepository.findAll();

        log.info("result: {}", result);
        if(result.isEmpty()) {
            return;
        }
        gcsService.deleteFileFromGCSByUrlList(result.stream().map(FileEvent::getFileName).collect(Collectors.toList()));
        fileEventRepository.deleteAll();
    }








}
