package elegant.children.catchculture.service.culturalEvent;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventReportDTO;
import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import elegant.children.catchculture.entity.eventreport.EventReport;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventReportRepository;
import elegant.children.catchculture.service.GCS.GCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CulturalEventReportService {


    private final CulturalEventReportRepository culturalEventReportRepository;
    private final GCSService gcsService;


    public String createEventReport(CulturalEventReportDTO culturalEventReportDTO, List<MultipartFile> fileList, User user) throws IOException {
        List<String> storedFileUrl = new ArrayList<>();
        if (fileList != null && !fileList.isEmpty()) {
            storedFileUrl = gcsService.uploadImages(fileList);
        }
        CulturalEventDetail culturalEventDetail = CulturalEventDetail.builder()
                .category(culturalEventReportDTO.getCategory())
//                .reservationLink(culturalEventReportDTO.getReservationLink())
                .title(culturalEventReportDTO.getEventName())
                .place(culturalEventReportDTO.getEventLocation())
                .startDate(culturalEventReportDTO.getStartDate().atStartOfDay())
                .endDate(culturalEventReportDTO.getEndDate().atStartOfDay())
                .isFree(culturalEventReportDTO.getIsFree())
                .description(culturalEventReportDTO.getDescription())
                .sns(culturalEventReportDTO.getSnsAddress())
                .telephone(culturalEventReportDTO.getPhoneNumber())
                .wayToCome(culturalEventReportDTO.getWayToCome())
                .storedFileUrl(storedFileUrl)
                .build();
        EventReport eventReport = EventReport.builder()
                .culturalEventDetail(culturalEventDetail)
                .user(user)
                .build();
        culturalEventReportRepository.save(eventReport);

        return eventReport.getCulturalEventDetail().toString();
    }

    public Page<CulturalEventReportDTO> getMyEventReports(User user, Pageable pageable) {
        Page<EventReport> reports = culturalEventReportRepository.findByUserId(user.getId(), pageable);
        return reports.map(this::convertToReportDTO);
    }

    public CulturalEventReportDTO convertToReportDTO(EventReport report) {
        return new CulturalEventReportDTO(
                report.getCulturalEventDetail().getTitle(),
                report.getCulturalEventDetail().getPlace(),
                report.getCulturalEventDetail().getStartDate().toLocalDate(),
                report.getCulturalEventDetail().getEndDate().toLocalDate(),
                report.getCulturalEventDetail().getIsFree(),
                report.getCulturalEventDetail().getCategory(),
                report.getCulturalEventDetail().getDescription(),
                report.getCulturalEventDetail().getSns(),
                report.getCulturalEventDetail().getTelephone(),
                report.getCulturalEventDetail().getWayToCome()
        );
    }

}
