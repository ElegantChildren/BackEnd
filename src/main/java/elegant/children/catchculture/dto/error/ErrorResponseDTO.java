package elegant.children.catchculture.dto.error;

import elegant.children.catchculture.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class ErrorResponseDTO {

    private ErrorCode code;
    private String message;

    public static ErrorResponseDTO of(ErrorCode errorCode) {
        return ErrorResponseDTO.builder()
                .code(errorCode)
                .message(errorCode.getMessage())
                .build();
    }
}
