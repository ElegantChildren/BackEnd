package elegant.children.catchculture.dto.user;

import elegant.children.catchculture.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponseDTO {

    private String storedFileUrl;
    private String nickname;
    private Role role;

    public static UserProfileResponseDTO of(final String storedFileUrl, final String nickname, final Role role) {
        return UserProfileResponseDTO.builder()
                .storedFileUrl(storedFileUrl)
                .nickname(nickname)
                .build();
    }
}
