package elegant.children.catchculture.controller;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventReportDTO;
import elegant.children.catchculture.dto.review.response.ReviewDTO;
import elegant.children.catchculture.dto.user.PointHistoryResponseDTO;
import elegant.children.catchculture.dto.user.PointUsageResponseDTO;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.service.culturalEvent.CulturalEventReportService;
import elegant.children.catchculture.service.pointHistory.PointHistoryService;
import elegant.children.catchculture.service.pointHistory.PointUsageService;
import elegant.children.catchculture.service.review.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Tag(name = "로그인 후 사용가능한 페이지", description = "마이 페이지")
@RequestMapping("/user")
@RestController
public class MyPageController {
    @Autowired
    private PointHistoryService pointHistoryService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CulturalEventReportService culturalEventReportService;
    @Autowired
    private PointUsageService pointUsageService;

    @GetMapping("/point-history")
    public ResponseEntity<Page<PointHistoryResponseDTO>> viewPointHistory(
            final @AuthenticationPrincipal User user,
            final @RequestParam(defaultValue = "0") int page,
            final @RequestParam(defaultValue = "7") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PointHistoryResponseDTO> pointHistories = pointHistoryService.getPointHistoryForUser(user, pageable);
        return ResponseEntity.ok(pointHistories);
    }

    @GetMapping("/point")
    public ResponseEntity<Integer> viewCurrentPoint(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.getPoint());
    }

    @GetMapping("/my-reviews")
    public ResponseEntity<Page<ReviewDTO>> viewMyReview(
            final @AuthenticationPrincipal User user,
            final @RequestParam(defaultValue = "0") int page,
            final @RequestParam(defaultValue = "7") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewDTO> reviewList = reviewService.getMyReviews(user, pageable);
        return ResponseEntity.ok(reviewList);

    }

    @GetMapping("/my-reports")
    public ResponseEntity<Page<CulturalEventReportDTO>> viewMyReport(
            final @AuthenticationPrincipal User user,
            final @RequestParam(defaultValue = "0") int page,
            final @RequestParam(defaultValue = "7") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CulturalEventReportDTO> reportList = culturalEventReportService.getMyEventReports(user, pageable);
        return ResponseEntity.ok(reportList);

    }

    @GetMapping("/point-usage")
    public ResponseEntity<List<PointUsageResponseDTO>> viewPointUsage(){
        return ResponseEntity.ok(pointUsageService.findPointUsage());
    }

}
