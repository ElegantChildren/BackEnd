package elegant.children.catchculture.common.filter;

import elegant.children.catchculture.common.utils.CookieUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class CustomCookieSettingFilter extends OncePerRequestFilter {

    private static final String URL = "/oauth2/authorization/**";


    @Value("${jwt.token.expiration}")
    private int tokenExpiration;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("CustomCookieSettingFilter");
        if (PatternMatchUtils.simpleMatch(URL, request.getRequestURI())) {
            final String redirectUrl = request.getParameter("redirect_url");
            log.info("redirectUrl: {}", redirectUrl);
            if (redirectUrl != null) {
                CookieUtils.addCookie(response, "redirect_url", redirectUrl, tokenExpiration);
            }
        }
        filterChain.doFilter(request, response);
    }
}
