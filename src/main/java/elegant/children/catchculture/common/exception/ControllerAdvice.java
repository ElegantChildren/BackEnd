package elegant.children.catchculture.common.exception;

import elegant.children.catchculture.dto.error.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static elegant.children.catchculture.common.exception.ErrorCode.*;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException e) {
        log.info("RuntimeException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponseDTO.of(INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatchException(RuntimeException e) {
        log.info("RuntimeException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseDTO.of(INVALID_CATEGORY));
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException e) {
        final ErrorCode errorCode = e.getErrorCode();
        log.info("CustomException: {}", errorCode);
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponseDTO.of(errorCode));

    }
}
