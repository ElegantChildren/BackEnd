package elegant.children.catchculture.service.fileEvent;

import elegant.children.catchculture.entity.fileEvent.FileEvent;
import elegant.children.catchculture.repository.fileEvent.FileEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileEventService {

    private final FileEventRepository fileEventRepository;


    @Transactional
    @EventListener
    public void handleFileEvent(final List<FileEvent> fileEventList) {
        fileEventRepository.saveAll(fileEventList);
    }








}
