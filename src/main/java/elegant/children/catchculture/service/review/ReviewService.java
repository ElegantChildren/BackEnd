package elegant.children.catchculture.service.review;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.dto.review.response.ReviewRatingResponseDTO;
import elegant.children.catchculture.dto.review.response.ReviewResponseDTO;
import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import elegant.children.catchculture.entity.review.Review;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventRepository;
import elegant.children.catchculture.repository.review.ReviewQueryRepository;
import elegant.children.catchculture.repository.review.ReviewRepository;
import elegant.children.catchculture.service.GCS.GCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewQueryRepository reviewQueryRepository;
    private final ReviewRepository reviewRepository;
    private final CulturalEventRepository culturalEventRepository;
    private final ReviewTransactionService reviewTransactionService;
    private final GCSService gcsService;

    public ReviewResponseDTO getUserReview(final int culturalEventId, final User user) {
        final Optional<Review> reviewOptional = reviewRepository.findByCulturalEventIdAndUserId(culturalEventId, user.getId());
        final ReviewResponseDTO userReviewResponseDTO;
        if(reviewOptional.isPresent()) {
            userReviewResponseDTO = ReviewResponseDTO.of(reviewOptional.get(), user);
        } else {
            userReviewResponseDTO = null;
        }
        return userReviewResponseDTO;
    }

    public ReviewRatingResponseDTO getReviewRating(final int culturalEventId) {
        return reviewQueryRepository.getReviewRating(culturalEventId);
    }


    public List<ReviewResponseDTO> getReviewList(final int culturalEventId, final User user, final int lastId) {
        return reviewQueryRepository.getReviewList(culturalEventId, user.getId(), lastId);
    }

    public void updateReviewDescription(final int reviewId, final String description) {
        reviewTransactionService.updateReviewDescription(reviewId, description);
    }

    public void deleteReview(final int reviewId) {
        reviewTransactionService.deleteReview(reviewId);
    }

    public void createReview(final int culturalEventId, final User user, final MultipartFile multipartFile,
                             final String description, final int rating) {

        final String storedImageUrl = gcsService.uploadImage(multipartFile);
        final CulturalEvent culturalEvent = culturalEventRepository.findById(culturalEventId).get();

        final Review review = Review.builder()
                .culturalEvent(culturalEvent)
                .user(user)
                .description(description)
                .rating(rating)
                .storedFileURL(storedImageUrl)
                .build();

        reviewTransactionService.createReview(review, user);


    }
}
