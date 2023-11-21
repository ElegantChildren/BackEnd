package elegant.children.catchculture.controller;

import elegant.children.catchculture.dto.review.response.ReviewRatingResponseDTO;
import elegant.children.catchculture.dto.review.response.ReviewResponseDTO;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/review")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{culturalEventId}/rating")
    public ReviewRatingResponseDTO getReviewRating(final @PathVariable int culturalEventId) {
        return reviewService.getReviewRating(culturalEventId);
    }

    @GetMapping("/{culturalEventId}/my-review")
    public ResponseEntity<ReviewResponseDTO> getUserReview(final @PathVariable  int culturalEventId,
                                                           final @AuthenticationPrincipal User user) {
        final ReviewResponseDTO userReviewResponseDTO = reviewService.getUserReview(culturalEventId, user);
        return ResponseEntity.ok(userReviewResponseDTO);
    }

    @GetMapping("/{culturalEventId}/list")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewList(final @PathVariable  int culturalEventId) {
        return ResponseEntity.ok(reviewService.getReviewList(culturalEventId));
    }
}
