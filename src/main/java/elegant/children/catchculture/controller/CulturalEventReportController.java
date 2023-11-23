package elegant.children.catchculture.controller;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventReportDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class CulturalEventReportController {


    @PostMapping(value = "/event/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> reportEvent(CulturalEventReportDTO culturalEventReportDTO) {


        return ResponseEntity.ok("Event reported successfully!");
    }
}
