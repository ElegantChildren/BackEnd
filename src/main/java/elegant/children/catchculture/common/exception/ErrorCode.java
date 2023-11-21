package elegant.children.catchculture.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    EMAIL_DUPLICATION("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),
    LOGIN_FAIL("다시 로그인 해주세요", HttpStatus.BAD_REQUEST),

    ALREADY_LIKE("이미 좋아요를 누른 문화 행사입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_STAR("이미 즐겨찾기를 누른 문화 행사입니다.", HttpStatus.BAD_REQUEST),
    INVALID_EVENT_ID("존재하지 않는 문화 행사 Id 입니다.", HttpStatus.NOT_FOUND),

    NOT_AUTHENTICATED("방문 인증이 되어 있지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS("관리자만 접근 가능합니다.", HttpStatus.FORBIDDEN),

    INTERNAL_SERVER_ERROR("서버 내부 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR);




    private String message;
    private HttpStatus status;


}


