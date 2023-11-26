package elegant.children.catchculture.event.review;

import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.entity.pointhistory.PointHistory;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.pointHistory.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CreateReviewEventHandler {

    private final PointHistoryRepository pointHistoryRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCreateReviewEvent(final CreateReviewEvent createReviewEvent) {
        final User user = createReviewEvent.getUser();
        final PointChange pointChange = createReviewEvent.getPointChange();

        final PointHistory pointHistory = PointHistory.builder()
                                .user(user)
                                .description(pointChange.getDescription())
                                .pointChange(pointChange.getPoint())
                                .build();
        pointHistoryRepository.save(pointHistory);
    }

}
