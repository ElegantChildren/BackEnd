package elegant.children.catchculture.controller;

import elegant.children.catchculture.repository.UserRepository;
import elegant.children.catchculture.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;

    @GetMapping("/test")
    public User main(@CookieValue(value = "Authorization", required = false) String token, @AuthenticationPrincipal User user) {
        log.info("user = {}", user);
        return user;
    }

    @GetMapping
    public String test() {
        return "test";
    }



}
