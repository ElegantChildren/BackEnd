package elegant.children.catchculture.common.security.oauth2.handler;

import elegant.children.catchculture.common.security.JwtTokenProvider;
import elegant.children.catchculture.common.security.oauth2.CustomOAuth2User;
import elegant.children.catchculture.common.utils.ClientUtils;
import elegant.children.catchculture.common.utils.CookieUtils;
import elegant.children.catchculture.common.utils.RedisUtils;
import elegant.children.catchculture.entity.user.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String REDIRECT_URL = "redirect_url";
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtils redisUtils;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String redirectUrl = "";
        log.info("OAuth2LoginSuccessHandler");
        log.info("authentication: {}", authentication);
        final CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        final String email = principal.getEmail();
        final Role role = principal.getRole();
        final String token = jwtTokenProvider.generateToken(email, role);
        jwtTokenProvider.sendJwtTokenCookie(response, token);
        redisUtils.setData(email, ClientUtils.getRemoteIP(request));
        response.sendRedirect("http://localhost:5173");
    }
}
