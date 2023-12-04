package elegant.children.catchculture.controller;

import elegant.children.catchculture.common.constant.Classification;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.dto.user.UserProfileResponseDTO;
import elegant.children.catchculture.entity.culturalevent.Category;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "로그인 후 사용가능한 페이지", description = "마이 페이지")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<User> userTest(@AuthenticationPrincipal User user) {
        if(user == null) {
            throw new RuntimeException("유저가 없습니다.");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(final @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(UserProfileResponseDTO.of(user.getStoredFileUrl(), user.getNickname()));
    }

    @PatchMapping("/profile/nickname")
    public void updateUserProfile(final @AuthenticationPrincipal User user, @RequestParam String nickName) {
        userService.updateUserNickname(user, nickName);
    }

    @GetMapping("/cultural-event")
    public ResponseEntity<Page<CulturalEventListResponseDTO>> getCulturalEventList(final @AuthenticationPrincipal User user,
                                                                                   final @RequestParam(required = false) List<Category> category,
                                                                                   final @RequestParam(required = false, defaultValue = "0") int offset,
                                                                                   final @RequestParam(required = false, defaultValue = "LIKE") Classification classification) {

        return ResponseEntity.ok(userService.getCulturalEventListWithUser(user, offset, category, classification));
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, final @AuthenticationPrincipal User user) {
        userService.logout(request, response, user);
    }


}
