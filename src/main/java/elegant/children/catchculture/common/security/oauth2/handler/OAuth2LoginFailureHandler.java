package elegant.children.catchculture.common.security.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import elegant.children.catchculture.common.exception.CustomException;
import elegant.children.catchculture.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;



@RequiredArgsConstructor
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){

        if (exception instanceof CustomException.AlreadyEmailExistException) {
            CustomException.sendError(objectMapper, response, ErrorCode.EMAIL_DUPLICATION);
            return;
        }

        CustomException.sendError(objectMapper, response, ErrorCode.LOGIN_FAIL);
    }
}
