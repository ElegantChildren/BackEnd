package elegant.children.catchculture.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileResponseDTO {

    private String storedFileUrl;
    private String nickname;

    public static UserProfileResponseDTO of(final String storedFileUrl, final String nickname) {
        return UserProfileResponseDTO.builder()
                .storedFileUrl(storedFileUrl)
                .nickname(nickname)
                .build();
    }
}
