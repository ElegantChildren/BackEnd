package elegant.children.catchculture.common.security.oauth2.userinfo;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(final Map<String, Object> attributes) {
        super(attributes);
        for (String s : attributes.keySet()) {
            log.info("key: " + s + " value: " + attributes.get(s));
        }
    }

    @Override
    public String getId() {
        Map<String, String> response = (Map<String, String>) attributes.get("response");
        return (response.get("id")).toString();
    }

    @Override
    public String getNickname() {
        final Map<String, String> response = (Map<String, String>) attributes.get("response");
        return response.get("name");
    }

    @Override
    public String getEmail() {
        final Map<String, String> response = (Map<String, String>) attributes.get("response");
        return response.get("email");
    }

    @Override
    public String getProfileImageURL() {
        final Map<String, String> response = (Map<String, String>) attributes.get("response");
        return response.get("profile_image");
    }
}
