package elegant.children.catchculture.controller;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventReportDTO;
import elegant.children.catchculture.dto.review.response.ReviewDTO;
import elegant.children.catchculture.dto.user.PointHistoryResponseDTO;
import elegant.children.catchculture.dto.user.PointUsageResponseDTO;
import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.service.culturalEvent.CulturalEventReportService;
import elegant.children.catchculture.service.pointHistory.PointGradeService;
import elegant.children.catchculture.service.pointHistory.PointHistoryService;
import elegant.children.catchculture.service.pointHistory.PointUsageService;
import elegant.children.catchculture.service.review.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private PointGradeService pointGradeService;

    @PostMapping(value = "/report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> reportEvent(final @AuthenticationPrincipal User user,
                                              final @RequestPart CulturalEventReportDTO culturalEventReportDTO,
                                              final @RequestPart(name = "fileList", required = false) List<MultipartFile> fileList) throws IOException {

        String response = culturalEventReportService.createEventReport(culturalEventReportDTO, fileList, user);

        return ResponseEntity.ok(response);
    }

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

    @PostMapping("/purchase-reward")
    public ResponseEntity<String> purchaseReward(
            final @AuthenticationPrincipal User user,
            final @RequestParam PointChange pointChange){
        return ResponseEntity.ok(pointHistoryService.purchaseReward(user,pointChange));
    }

    @GetMapping("/point-grade")
    public ResponseEntity<String> viewPointGrade(
            final @AuthenticationPrincipal User user){
        return ResponseEntity.ok(pointGradeService.calculateUserRank(user.getId()));
    }
}
