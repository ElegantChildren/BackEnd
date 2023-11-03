package elegant.children.catchculture;

import elegant.children.catchculture.entity.user.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WitchCustomMockUserSecurityContextFactory.class)
public @interface WithCustomMockUser {
    String email() default "aaaa@naver.com";
    Role role() default Role.USER;

}
