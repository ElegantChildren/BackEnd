package elegant.children.catchculture.controller;

import elegant.children.catchculture.common.annotation.AdminUser;
import elegant.children.catchculture.dto.admin.response.AdminEventReportResponseDTO;
import elegant.children.catchculture.dto.admin.response.AdminVisitAuthListResponseDTO;
import elegant.children.catchculture.service.eventReport.EventReportService;
import elegant.children.catchculture.service.visitAuth.VisitAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final VisitAuthService visitAuthService;
    private final EventReportService eventReportService;


    @AdminUser
    @GetMapping("/visit-auth/list")
    public ResponseEntity<Slice<AdminVisitAuthListResponseDTO>> getNotAuthenticatedVisitAuthList(final int lastId) {
        return ResponseEntity.ok(visitAuthService.getNotAuthenticatedVisitAuthList(lastId));
    }

    @AdminUser
    @PutMapping("/visit-auth/{visitAuthId}")
    public void authenticateVisitAuth(final @PathVariable int visitAuthId, final @RequestParam int userId,
                                      final @RequestParam int culturalEventId) {
        visitAuthService.authenticateVisitAuth(visitAuthId, userId, culturalEventId);
    }

    @AdminUser
    @GetMapping("/event-report/list")
    public ResponseEntity<Slice<AdminEventReportResponseDTO>> getEventReportList(final int lastId) {
        return ResponseEntity.ok(eventReportService.getEventReportList(lastId));
    }

    @AdminUser
    @PostMapping("/event-report/{eventReportId}")
    public void createCulturalEvent(final @PathVariable int eventReportId, final @RequestParam int userId) {
        eventReportService.createCulturalEvent(eventReportId, userId);
    }

}
