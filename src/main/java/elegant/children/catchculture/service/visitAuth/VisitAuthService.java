package elegant.children.catchculture.service.visitAuth;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.dto.admin.response.VisitAuthResponseDTO;
import elegant.children.catchculture.dto.admin.response.VisitAuthResponseListDTO;
import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import elegant.children.catchculture.entity.pointhistory.PointChange;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.entity.visitauth.VisitAuth;
import elegant.children.catchculture.event.CreatePointHistoryEvent;
import elegant.children.catchculture.event.SignOutEvent;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventRepository;
import elegant.children.catchculture.repository.visiatAuth.VisitAuthQueryRepository;
import elegant.children.catchculture.repository.visiatAuth.VisitAuthRepository;
import elegant.children.catchculture.service.GCS.GCSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VisitAuthService {

    private final VisitAuthQueryRepository visitAuthQueryRepository;
    private final VisitAuthRepository visitAuthRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final CulturalEventRepository culturalEventRepository;
    private final GCSService gcsService;

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

    @Transactional
    public void createVisitAuth(final User user, final int eventId, final List<MultipartFile> files){
        visitAuthRepository.findByUserAndCulturalEvent(user.getId(),eventId)
                .ifPresent(visitAuth -> {
                    throw new CustomException(ErrorCode.ALREADY_VISIT_AUTH_SUBMITTED);
                });
        final CulturalEvent culturalEvent = culturalEventRepository.findById(eventId).get();
        final List<String> storedFileUrl = gcsService.uploadImages(files);
        final VisitAuth visitAuth = VisitAuth.builder()
                .culturalEvent(culturalEvent)
                .createdAt(LocalDateTime.now())
                .user(user)
                .isAuthenticated(false)
                .storedFileUrl(storedFileUrl)
                .build();
        visitAuthRepository.save(visitAuth);

    }

    @Transactional
    @EventListener
    public void handleSignOutEvent(final SignOutEvent signOutEvent) {
        log.info("handleSignOutEvent");
        visitAuthRepository.deleteByUserId(signOutEvent.getUserId());
    }

}
