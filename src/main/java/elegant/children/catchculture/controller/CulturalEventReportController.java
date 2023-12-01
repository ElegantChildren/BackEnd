package elegant.children.catchculture.controller;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventReportDTO;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.service.culturalEvent.CulturalEventReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "로그인 후 사용가능한 페이지", description = "마이 페이지")
@RestController
@RequiredArgsConstructor //생성자 자동 생성
public class CulturalEventReportController {
    private final CulturalEventReportService culturalEventReportService;

    String response;

    @PostMapping(value = "/user/report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> reportEvent(final @AuthenticationPrincipal User user,
                                              final @RequestPart CulturalEventReportDTO culturalEventReportDTO,
                                              final @RequestPart(name = "fileList", required = false) List<MultipartFile> fileList
                                              ) throws IOException {

        response = culturalEventReportService.createEventReport(culturalEventReportDTO,fileList,user);

        return ResponseEntity.ok(response);
    }

}
