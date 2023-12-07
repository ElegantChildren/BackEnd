package elegant.children.catchculture.service.review;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.entity.review.Review;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.review.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewTransactionServiceTest {

    @InjectMocks
    private ReviewTransactionService target;

    @Mock
    private ReviewRepository reviewRepository;


    private Optional<Review> getReview() {
        return Optional.of(Review.builder()
                .description("test")
                .id(1)
                .build());
    }

    @Test
    void updateReviewDescription() {

        final Optional<Review> review = getReview();

        doReturn(review).when(reviewRepository).findById(any(Integer.class));
        doNothing().when(reviewRepository).updateReviewDescription(any(Integer.class), any(String.class));

        target.updateReviewDescription(1, "test");

        verify(reviewRepository, times(1)).findById(any(Integer.class));
        verify(reviewRepository, times(1)).updateReviewDescription(any(Integer.class), any(String.class));

    }



    @Test
    void updateReviewDescriptionWithException() {

        doReturn(Optional.empty()).when(reviewRepository).findById(any(Integer.class));

        final CustomException customException = assertThrows(CustomException.class, () -> target.updateReviewDescription(1, "test"));

        Assertions.assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.INVALID_REVIEW_ID);
        verify(reviewRepository, times(1)).findById(any(Integer.class));
        verify(reviewRepository, times(0)).updateReviewDescription(any(Integer.class), any(String.class));
    }

    @Test
    void deleteReviewTest() {
        final Optional<Review> review = getReview();

        doReturn(review).when(reviewRepository).findById(any(Integer.class));

        target.deleteReview(any(Integer.class));

        verify(reviewRepository, times(1)).findById(any(Integer.class));
    }

    @Test
    void deleteReviewTestWithException() {

        doReturn(Optional.empty()).when(reviewRepository).findById(any(Integer.class));

        final CustomException customException = assertThrows(CustomException.class, () -> target.deleteReview(1));

        Assertions.assertThat(customException.getErrorCode()).isEqualTo(ErrorCode.INVALID_REVIEW_ID);
        verify(reviewRepository, times(1)).findById(any(Integer.class));
        verify(reviewRepository, times(0)).updateReviewDescription(any(Integer.class), any(String.class));
    }

    @Test
    void createReviewTest() {
        doReturn(Review.builder().build()).when(reviewRepository).save(any(Review.class));

        target.createReview(Review.builder().build(), User.builder().build());

        verify(reviewRepository, times(1)).save(any(Review.class));
    }





}