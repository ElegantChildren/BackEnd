package elegant.children.catchculture.controller;

import elegant.children.catchculture.common.annotation.AuthenticatedVisitAuth;
import elegant.children.catchculture.dto.review.request.CreateReviewRequestDTO;
import elegant.children.catchculture.dto.review.request.DeleteReviewRequestDTO;
import elegant.children.catchculture.dto.review.request.UpdateReviewRequestDTO;
import elegant.children.catchculture.dto.review.response.ReviewRatingResponseDTO;
import elegant.children.catchculture.dto.review.response.ReviewResponseDTO;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<List<ReviewResponseDTO>> getReviewList(final @AuthenticationPrincipal User user,
                                                                 final @PathVariable  int culturalEventId,
                                                                 final @RequestParam(required = false, defaultValue = "0") int lastId) {
        return ResponseEntity.ok(reviewService.getReviewList(culturalEventId, user, lastId));
    }

    @PostMapping(value = "/{culturalEventId}/my-review", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @AuthenticatedVisitAuth
    public void createReview(final @PathVariable int culturalEventId, final @AuthenticationPrincipal User user,
                             final @RequestPart("file") MultipartFile multipartFile, final @RequestPart("reviewDetail") CreateReviewRequestDTO createReviewRequestDTO) {
        reviewService.createReview(culturalEventId, user, multipartFile, createReviewRequestDTO.getDescription(), createReviewRequestDTO.getRating());
    }
    /***
     * Event 가 있어야 되고 인증이 되있어야 함.
     */
    @PutMapping("/{culturalEventId}/my-review")
    @AuthenticatedVisitAuth
    public void updateReviewDescription(final @PathVariable int culturalEventId, @AuthenticationPrincipal User user,
                                        final @RequestBody UpdateReviewRequestDTO updateReviewRequestDTO) {
        reviewService.updateReviewDescription(updateReviewRequestDTO.getReviewId(), updateReviewRequestDTO.getDescription());
    }

    @DeleteMapping("/{culturalEventId}/my-review")
    @AuthenticatedVisitAuth
    public void deleteReview(final @PathVariable int culturalEventId, final @AuthenticationPrincipal User user,
                             final @RequestBody DeleteReviewRequestDTO deleteReviewRequestDTO) {
        reviewService.deleteReview(deleteReviewRequestDTO.getReviewId());
    }

}
