package elegant.children.catchculture.common.aop;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.VisitAuthRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticatedVisitAuthAspect {


    private final VisitAuthRepository visitAuthRepository;

    @Before("@annotation(elegant.children.catchculture.common.annotation.AuthenticatedVisitAuth) && args(user, culturalEventId)")
    public void isCulturalEventAuthenticated(final User user, final int culturalEventId) {
        visitAuthRepository.isAuthenticated(user.getId(), culturalEventId)
                .ifPresentOrElse(
                        isAuthenticated -> {
                            if(!isAuthenticated)
                                throw new CustomException(ErrorCode.NOT_AUTHENTICATED);
                        },
                        () -> {
                            throw new CustomException(ErrorCode.NOT_AUTHENTICATED);
                        }
                );



    }
}
