package elegant.children.catchculture.entity.user;

import java.util.Arrays;


public enum SocialType {

    KAKAO,
    NAVER,
    GOOGLE;

    public static SocialType of(String socialType) {
        return Arrays.stream(SocialType.values())
                .filter(type -> type.name().equalsIgnoreCase(socialType))
                .findAny()
                .orElse(null);
    }
}
