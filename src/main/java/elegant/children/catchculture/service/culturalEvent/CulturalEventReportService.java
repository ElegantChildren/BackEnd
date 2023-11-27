package elegant.children.catchculture.service.culturalEvent;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventReportDTO;
import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import elegant.children.catchculture.entity.eventreport.EventReport;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventReportRepository;
import elegant.children.catchculture.service.GCS.GCSService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CulturalEventReportService {


    private final CulturalEventReportRepository culturalEventReportRepository;
    private final GCSService gcsService;


    public String createEventReport(CulturalEventReportDTO culturalEventReportDTO, List<MultipartFile> fileList, User user) throws IOException {
        CulturalEventDetail culturalEventDetail = CulturalEventDetail.builder()
//                .category(culturalEventReportDTO.getCategory())
//                .reservationLink(culturalEventReportDTO.getReservationLink())
                .title(culturalEventReportDTO.getEventName())
                .place(culturalEventReportDTO.getEventLocation())
                .startDate(culturalEventReportDTO.getStartDate().atStartOfDay())
                .endDate(culturalEventReportDTO.getEndDate().atStartOfDay())
                .isFree(culturalEventReportDTO.isFree())
                .description(culturalEventReportDTO.getDescription())
                .sns(culturalEventReportDTO.getSnsAddress())
                .telephone(culturalEventReportDTO.getPhoneNumber())
                .wayToCome(culturalEventReportDTO.getWayToCome())
                .storedFileUrl(gcsService.uploadImages(fileList))
                .build();
        EventReport eventReport = EventReport.builder()
                .culturalEventDetail(culturalEventDetail)
                .user(user)
                .build();
        culturalEventReportRepository.save(eventReport);

        return eventReport.getCulturalEventDetail().toString();
    }

}
