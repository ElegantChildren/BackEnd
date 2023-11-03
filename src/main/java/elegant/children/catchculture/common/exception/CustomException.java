package elegant.children.catchculture.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomException extends RuntimeException{

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    @Getter
    public static class AlreadyEmailExistException extends AuthenticationException {
        private ErrorCode errorCode;

        public AlreadyEmailExistException(final ErrorCode errorCode) {
            super(errorCode.getMessage());
            this.errorCode = errorCode;
        }
    }

    public static void sendError(final ObjectMapper objectMapper, final HttpServletResponse response, final ErrorCode errorCode) {
        final Map<String, Object> errorMap = new HashMap<>();
        response.setStatus(errorCode.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        errorMap.put("code", errorCode.name());
        errorMap.put("message", errorCode.getMessage());
        try {
            final PrintWriter writer = response.getWriter();
            objectMapper.writeValue(writer, errorMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }






}
