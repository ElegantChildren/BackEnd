package elegant.children.catchculture.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    EMAIL_DUPLICATION("이미 존재하는 이메일입니다.", 400),
    LOGIN_FAIL("다시 로그인 해주세요", 400);


    private String message;
    private int status;


}


