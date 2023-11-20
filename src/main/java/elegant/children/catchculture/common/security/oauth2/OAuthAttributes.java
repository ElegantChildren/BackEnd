package elegant.children.catchculture.common.security.oauth2;

import elegant.children.catchculture.common.security.oauth2.userinfo.GoogleOAuth2UserInfo;
import elegant.children.catchculture.common.security.oauth2.userinfo.KakaoOAuth2UserInfo;
import elegant.children.catchculture.common.security.oauth2.userinfo.NaverOAuth2UserInfo;
import elegant.children.catchculture.common.security.oauth2.userinfo.OAuth2UserInfo;
import elegant.children.catchculture.entity.user.Role;
import elegant.children.catchculture.entity.user.SocialType;
import elegant.children.catchculture.entity.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oAuth2UserInfo;

    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oAuth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oAuth2UserInfo = oAuth2UserInfo;
    }

    /**
     * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
     * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
     * 소셜별 of 메소드(ofGoogle, ofKaKao, ofNaver)들은 각각 소셜 로그인 API에서 제공하는
     * 회원의 식별값(id), attributes, nameAttributeKey를 저장 후 build
     */
    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        if (socialType.equals(SocialType.GOOGLE)) {
            return ofGoogle(userNameAttributeName, attributes);
        }

        if (socialType.equals(SocialType.NAVER)) {
            return ofNaver(userNameAttributeName, attributes);
        }

        return ofKakao(userNameAttributeName, attributes);

    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();

    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oAuth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }



    public User toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .socialType(socialType)
                .email(oauth2UserInfo.getEmail())
                .nickname(oauth2UserInfo.getNickname())
                .storedFileUrl(oauth2UserInfo.getProfileImageURL())
                .role(Role.USER)
                .build();
    }


}