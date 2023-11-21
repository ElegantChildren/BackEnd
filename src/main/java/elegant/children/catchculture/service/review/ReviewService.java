package elegant.children.catchculture.service.review;

import elegant.children.catchculture.dto.review.response.ReviewRatingResponseDTO;
import elegant.children.catchculture.dto.review.response.ReviewResponseDTO;
import elegant.children.catchculture.entity.review.Review;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.review.ReviewQueryRepository;
import elegant.children.catchculture.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
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
}
