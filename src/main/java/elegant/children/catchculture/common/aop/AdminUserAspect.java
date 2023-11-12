package elegant.children.catchculture.common.aop;

import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.entity.user.Role;
import elegant.children.catchculture.entity.user.User;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminUserAspect {

    @Before("@annotation(elegant.children.catchculture.common.annotation.AdminUser)")
    public void setLoginUser() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.getRole().equals(Role.ADMIN))
            throw new CustomException(ErrorCode.INVALID_ACCESS);
    }
}
