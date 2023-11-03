package elegant.children.catchculture;

import elegant.children.catchculture.entity.user.Role;
import elegant.children.catchculture.entity.user.User;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WitchCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {


    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        final String email = annotation.email();
        final Role role = annotation.role();

        final User user = User.builder()
                .id(1)
                .email(email)
                .role(role)
                .build();

        RememberMeAuthenticationToken token = new RememberMeAuthenticationToken(email, user, List.of(new SimpleGrantedAuthority(role.name())));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
