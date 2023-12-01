package elegant.children.catchculture.service.review;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.dto.review.response.ReviewDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewQueryRepository reviewQueryRepository;
    private final ReviewRepository reviewRepository;
    private final CulturalEventRepository culturalEventRepository;
    private final ReviewTransactionService reviewTransactionService;
    private final GCSService gcsService;


    public ReviewResponseDTO getUserReview(final int culturalEventId, final User user) {
        final Optional<Review> reviewOptional = reviewRepository.findByCulturalEventIdAndUserId(culturalEventId, user.getId());
        final ReviewResponseDTO userReviewResponseDTO;
        userReviewResponseDTO = reviewOptional.map(review -> ReviewResponseDTO.of(review, user)).orElse(null);
        return userReviewResponseDTO;
    }

    public ReviewRatingResponseDTO getReviewRating(final int culturalEventId) {
        return reviewQueryRepository.getReviewRating(culturalEventId);
    }

    public Slice<ReviewResponseDTO> getReviewList(final int culturalEventId, final User user, final int lastId) {
        return reviewQueryRepository.getReviewList(culturalEventId, user.getId(), lastId);
    }

    public void updateReviewDescription(final int reviewId, final String description) {
        reviewTransactionService.updateReviewDescription(reviewId, description);
    }


    public void deleteReview(final int reviewId) {
        reviewTransactionService.deleteReview(reviewId);
    }

    public void createReview(final int culturalEventId, final User user, final List<MultipartFile> multipartFile,
                             final String description, final int rating) {

        if(rating < 1 || rating > 5) {
            throw new CustomException(ErrorCode.INVALID_REVIEW_RATING);
        }

        reviewRepository.findByCulturalEventIdAndUserId(culturalEventId, user.getId())
                .ifPresent(review -> {
                    throw new CustomException(ErrorCode.ALREADY_REVIEW);
                });

        final List<String> storedImageUrl = gcsService.uploadImages(multipartFile);

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

//    public Page<ReviewDTO> getMyReviewList(final User user, final Pageable pageable){
//        return reviewRepository.getMyList(user.getId(), pageable);
//
//
//    }
    public Page<ReviewDTO> getMyReviews(User user, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByUserId(user.getId(), pageable);
        return reviews.map(this::convertToReviewDTO);
    }

    public ReviewDTO convertToReviewDTO(Review review) {
        String culturalEventTitle = review.getCulturalEvent() != null ? review.getCulturalEvent().getCulturalEventDetail().getTitle() : null;
        List<String> eventStoredFileUrl = review.getCulturalEvent() != null ? review.getCulturalEvent().getCulturalEventDetail().getStoredFileUrl() : null;

        return new ReviewDTO(
                review.getId(),
                review.getUser().getNickname(), // User 객체에서 닉네임을 가져옴
                review.getCreatedAt(),
                review.getDescription(),
                review.getStoredFileURL(),
                review.getRating(),
                culturalEventTitle,             // CulturalEventDetail 객체에서 제목을 가져옴
                eventStoredFileUrl              // CulturalEventDetail 객체에서 저장된 파일 URL을 가져옴
        );
    }

}
