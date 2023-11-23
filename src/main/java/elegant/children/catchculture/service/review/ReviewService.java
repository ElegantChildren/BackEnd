package elegant.children.catchculture.service.review;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.dto.review.response.ReviewRatingResponseDTO;
import elegant.children.catchculture.dto.review.response.ReviewResponseDTO;
import elegant.children.catchculture.entity.review.Review;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.review.ReviewQueryRepository;
import elegant.children.catchculture.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewQueryRepository reviewQueryRepository;
    private final ReviewRepository reviewRepository;

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

    @Transactional
    public void updateReviewDescription(final int reviewId, final String description) {
        reviewRepository.findById(reviewId)
                .ifPresentOrElse(review -> {
                    reviewRepository.updateReviewDescription(reviewId, description);
                }, () -> {
                    throw new CustomException(ErrorCode.INVALID_REVIEW_ID);
                });
    }

    @Transactional
    public void deleteReview(final int reviewId) {
        reviewRepository.findById(reviewId)
                        .ifPresentOrElse(review -> {
                                    reviewRepository.deleteReviewById(reviewId);
                                }, () -> {
                            throw new CustomException(ErrorCode.INVALID_REVIEW_ID);
                        });
    }
}
