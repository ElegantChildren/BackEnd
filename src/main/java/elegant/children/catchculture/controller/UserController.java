package elegant.children.catchculture.controller;

import elegant.children.catchculture.entity.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "로그인 후 사용가능한 페이지", description = "마이 페이지")
@RequestMapping()
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public ResponseEntity<User> userTest(@AuthenticationPrincipal User user) {
        if(user == null) {
            throw new RuntimeException("유저가 없습니다.");
        }
        return ResponseEntity.ok(user);
    }
}
