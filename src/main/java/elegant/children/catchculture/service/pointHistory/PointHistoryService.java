package elegant.children.catchculture.service.pointHistory;

import elegant.children.catchculture.dto.user.PointHistoryResponseDTO;
import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.entity.pointhistory.PointHistory;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.event.CreatePointHistoryEvent;
import elegant.children.catchculture.event.CreateCulturalEvent;
import elegant.children.catchculture.repository.pointHistory.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
@Slf4j
public class PointHistoryService {
    
    private final PointHistoryRepository pointHistoryRepository;
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCreateReviewEvent(final CreateCulturalEvent createCulturalEvent) {
        log.info("Async THREAD NAME : {}", Thread.currentThread().getName());
        log.info("handleCreateReviewEvent");
        savePointHistory(createCulturalEvent.getUser(), createCulturalEvent.getPointChange());
    }



    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAuthenticateVisitAuthEvent(final CreatePointHistoryEvent authenticateVisitAuthEvent) {
        log.info("Async THREAD NAME : {}", Thread.currentThread().getName());
        log.info("handleCreateReviewEvent");
        savePointHistory(authenticateVisitAuthEvent.getUser(), authenticateVisitAuthEvent.getPointChange());
    }

    private void savePointHistory(User user, PointChange pointChange) {
        final PointHistory pointHistory = PointHistory.builder()
                .user(user)
                .description(pointChange.getDescription())
                .pointChange(pointChange.getPoint())
                .build();
        pointHistoryRepository.save(pointHistory);
    }

    public Page<PointHistoryResponseDTO> getPointHistoryForUser(User user, Pageable pageable) {
        return pointHistoryRepository.findHistories(user.getId(), pageable);
    }
}
