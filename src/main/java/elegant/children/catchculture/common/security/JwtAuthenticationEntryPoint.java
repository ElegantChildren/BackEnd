package elegant.children.catchculture.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        log.info("JwtAuthenticationEntryPoint");
        CustomException.sendError(objectMapper, response, ErrorCode.LOGIN_FAIL);
    }
}
