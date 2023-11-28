package elegant.children.catchculture.service.visitAuth;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.dto.admin.response.VisitAuthResponseDTO;
import elegant.children.catchculture.dto.admin.response.VisitAuthResponseListDTO;
import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.entity.visitauth.VisitAuth;
import elegant.children.catchculture.event.CreatePointHistoryEvent;
import elegant.children.catchculture.repository.visiatAuth.VisitAuthQueryRepository;
import elegant.children.catchculture.repository.visiatAuth.VisitAuthRepository;
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
public class VisitAuthService {

    private final VisitAuthQueryRepository visitAuthQueryRepository;
    private final VisitAuthRepository visitAuthRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Slice<VisitAuthResponseListDTO> getNotAuthenticatedVisitAuthList(final int lastId) {
        return visitAuthQueryRepository.getNotAuthenticatedVisitAuthList(lastId);
    }


    public VisitAuthResponseDTO getVisitAuth(final int visitAuthId) {
        return visitAuthQueryRepository.findById(visitAuthId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_VISIT_AUTH_ID));
    }

    @Transactional
    public void authenticateVisitAuth(final int visitAuthId, final int userId, final int culturalEventId) {
        final VisitAuth visitAuth = visitAuthRepository.findByIdWithUserAAndCulturalEvent(visitAuthId, userId, culturalEventId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_VISIT_AUTH_ID));
        visitAuth.authenticate();
        applicationEventPublisher.publishEvent(new CreatePointHistoryEvent(PointChange.VISIT_AUTH, visitAuth.getUser()));
    }
}
