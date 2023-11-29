package elegant.children.catchculture.controller;

import elegant.children.catchculture.common.annotation.AdminUser;
import elegant.children.catchculture.dto.admin.response.EventReportResponseDTO;
import elegant.children.catchculture.dto.admin.response.EventReportResponseListDTO;
import elegant.children.catchculture.dto.admin.response.VisitAuthResponseDTO;
import elegant.children.catchculture.dto.admin.response.VisitAuthResponseListDTO;
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
    public ResponseEntity<Slice<VisitAuthResponseListDTO>> getNotAuthenticatedVisitAuthList(final int lastId) {
        return ResponseEntity.ok(visitAuthService.getNotAuthenticatedVisitAuthList(lastId));
    }

    @AdminUser
    @GetMapping("/visit-auth/{visitAuthId}")
    public ResponseEntity<VisitAuthResponseDTO> getVisitAuth(final @PathVariable int visitAuthId) {
        return ResponseEntity.ok(visitAuthService.getVisitAuth(visitAuthId));
    }

    @AdminUser
    @PutMapping("/visit-auth/{visitAuthId}")
    public void authenticateVisitAuth(final @PathVariable int visitAuthId, final @RequestParam int userId,
                                      final @RequestParam int culturalEventId) {
        visitAuthService.authenticateVisitAuth(visitAuthId, userId, culturalEventId);
    }

    @AdminUser
    @GetMapping("/event-report/list")
    public ResponseEntity<Slice<EventReportResponseListDTO>> getEventReportList(final @RequestParam int lastId) {
        return ResponseEntity.ok(eventReportService.getEventReportList(lastId));
    }

    @AdminUser
    @GetMapping("/event-report/{eventReportId}")
    public ResponseEntity<EventReportResponseDTO> getEventReport(final @PathVariable int eventReportId) {
        return ResponseEntity.ok(eventReportService.getEventReport(eventReportId));
    }



    @AdminUser
    @PostMapping("/event-report/{eventReportId}")
    public void createCulturalEvent(final @PathVariable int eventReportId, final @RequestParam int userId) {
        eventReportService.createCulturalEvent(eventReportId, userId);
    }

}
