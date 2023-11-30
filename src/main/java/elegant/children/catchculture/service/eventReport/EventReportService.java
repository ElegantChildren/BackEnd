package elegant.children.catchculture.service.eventReport;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.dto.admin.response.EventReportResponseDTO;
import elegant.children.catchculture.dto.admin.response.EventReportResponseListDTO;
import elegant.children.catchculture.entity.eventreport.EventReport;
import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.event.CreateCulturalEvent;
import elegant.children.catchculture.repository.eventReport.EventReportQueryRepository;
import elegant.children.catchculture.repository.eventReport.EventReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EventReportService {

    private final EventReportQueryRepository eventReportQueryRepository;
    private final EventReportRepository eventReportRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Slice<EventReportResponseListDTO> getEventReportList(final int lastId) {
        return eventReportQueryRepository.getEventReportList(lastId);
    }

    public EventReportResponseDTO getEventReport(final int eventReportId) {

        return eventReportQueryRepository.getEventReport(eventReportId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EVENT_REPORT_ID));

    }

    @Transactional
    public void createCulturalEvent(final int eventReportId, final int userId) {
        final EventReport eventReport = eventReportRepository.findByUserId(eventReportId, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_EVENT_REPORT_ID));
        eventReportRepository.delete(eventReport);
        eventReportRepository.flush();
        applicationEventPublisher.publishEvent(new CreateCulturalEvent(eventReport.getCulturalEventDetail(), eventReport.getUser()
                                                                        , PointChange.CREATE_CULTURAL_EVENT));
    }
}
