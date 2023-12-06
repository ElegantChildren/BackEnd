package elegant.children.catchculture.service.review;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.entity.fileEvent.FileEvent;
import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.entity.review.Review;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.event.CreatePointHistoryEvent;
import elegant.children.catchculture.event.DeleteFileEvent;
import elegant.children.catchculture.event.SignOutEvent;
import elegant.children.catchculture.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewTransactionService {

    private final ReviewRepository reviewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void updateReviewDescription(final int reviewId, final String description) {
        reviewRepository.findById(reviewId)
                .ifPresentOrElse(review -> reviewRepository.updateReviewDescription(reviewId, description), () -> {
                    throw new CustomException(ErrorCode.INVALID_REVIEW_ID);
                });
    }

    public void deleteReview(final int reviewId) {
        reviewRepository.findById(reviewId)
                .ifPresentOrElse(review -> reviewRepository.deleteReviewById(reviewId), () -> {
                    throw new CustomException(ErrorCode.INVALID_REVIEW_ID);
                });
    }

    public void createReview(final Review review, final User user) {
        reviewRepository.save(review);
        applicationEventPublisher.publishEvent(new CreatePointHistoryEvent(PointChange.REVIEW, user));
    }

    @EventListener
    public void handleSighOutEvent(final SignOutEvent signOutEvent) {
        log.info("handleSighOutEvent");

        final List<Review> reviews = reviewRepository.findByUserId(signOutEvent.getUserId());
        if(reviews.isEmpty()) {
            return;
        }

        final List<FileEvent> fileEvents = reviews.stream().map(Review::getStoredFileURL)
                .flatMap(Collection::stream)
                .map(fileUrl -> FileEvent.builder().fileName(fileUrl).build())
                .collect(Collectors.toList());


        applicationEventPublisher.publishEvent(new DeleteFileEvent(fileEvents));
        reviewRepository.deleteByUserId(signOutEvent.getUserId());
    }
}
