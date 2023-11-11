package elegant.children.catchculture.common.aop;

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
            throw new RuntimeException("관리자만 접근 가능합니다.");
    }
}
