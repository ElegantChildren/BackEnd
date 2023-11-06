package elegant.children.catchculture.common.security.oauth2.handler;

import elegant.children.catchculture.common.security.JwtTokenProvider;
import elegant.children.catchculture.common.security.oauth2.CustomOAuth2User;
import elegant.children.catchculture.common.utils.ClientUtils;
import elegant.children.catchculture.common.utils.CookieUtils;
import elegant.children.catchculture.common.utils.RedisUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Optional;

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
//        final Optional<Cookie> cookie = CookieUtils.getCookie(request, REDIRECT_URL);
//        if (cookie.isPresent()) {
//            redirectUrl = CookieUtils.deserialize(cookie.get().getValue(), String.class);
//            CookieUtils.deleteCookie(request, response, REDIRECT_URL);
//        }
        final CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        final String token = jwtTokenProvider.generateToken(principal.getEmail(), principal.getRole());
        jwtTokenProvider.sendJwtTokenCookie(response, token);
        redisUtils.setData(token, ClientUtils.getRemoteIP(request), (long) (60 * 60 * 24 * 7));
        response.sendRedirect("http://localhost:8080");
    }
}
