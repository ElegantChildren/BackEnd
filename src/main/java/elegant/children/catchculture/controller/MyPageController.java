package elegant.children.catchculture.controller;

import elegant.children.catchculture.dto.user.PointHistoryResponseDTO;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.service.pointHistory.PointHistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequestMapping("/user")
@RestController
public class MyPageController {

    @Autowired
    private PointHistoryService pointHistoryService;

//    @Tag(name = "포인트 기록")
    @GetMapping("/point-history")
    public ResponseEntity<Page<PointHistoryResponseDTO>> viewPointHistory(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PointHistoryResponseDTO> pointHistories = pointHistoryService.getPointHistoryForUser(user, pageable);
        return ResponseEntity.ok(pointHistories);
    }

//    @Tag(name = "현재 포인트")
    @GetMapping("/point")
    public ResponseEntity<Integer> viewCurrentPoint(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user.getPoint());
    }

}