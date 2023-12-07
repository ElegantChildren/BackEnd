package elegant.children.catchculture.common.aop;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventQueryRepository;
import elegant.children.catchculture.repository.visiatAuth.VisitAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticatedVisitAuthAspect {

    private final CulturalEventQueryRepository culturalEventQueryRepository;
    private final VisitAuthRepository visitAuthRepository;

    @Before(value = "@annotation(elegant.children.catchculture.common.annotation.AuthenticatedVisitAuth) && args(culturalEventId, user, ..)", argNames = "culturalEventId,user")
    public void isCulturalEventAuthenticated(final int culturalEventId, final User user ) {
        log.info("isCulturalEventAuthenticated");
        if (culturalEventQueryRepository.existById(culturalEventId)) {
            visitAuthRepository.isAuthenticated(user.getId(), culturalEventId)
                    .ifPresentOrElse(
                            isAuthenticated -> {
                                if (!isAuthenticated)
                                    throw new CustomException(ErrorCode.NOT_AUTHENTICATED);
                            },
                            () -> {
                                throw new CustomException(ErrorCode.NOT_AUTHENTICATED);
                            }
                    );

        } else {
            log.info("not existById");
            throw new CustomException(ErrorCode.INVALID_EVENT_ID);
        }
    }
}
