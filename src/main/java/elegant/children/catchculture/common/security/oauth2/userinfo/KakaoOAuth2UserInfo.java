package elegant.children.catchculture.common.security.oauth2.userinfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{


    public KakaoOAuth2UserInfo(final Map<String, Object> attributes) {
        super(attributes);
        for (String s : attributes.keySet()) {
            System.out.println("key: " + s + " value: " + attributes.get(s));
        }
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getNickname() {
        final Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        final Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (account == null || profile == null) {
            return null;
        }

        return (String) profile.get("nickname");
    }

    @Override
    public String getEmail() {
        final Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");

        if (account == null) {
            return null;
        }

        return account.get("email").toString();
    }

    @Override
    public String getProfileImageURL() {
        final Map<String, Object> account = (Map<String, Object>) attributes.get("properties");


        return account.get("profile_image").toString();
    }
}
