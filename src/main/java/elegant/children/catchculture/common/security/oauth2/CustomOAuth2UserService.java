package elegant.children.catchculture.common.security.oauth2;

import elegant.children.catchculture.common.exception.ErrorCode;
import elegant.children.catchculture.entity.user.SocialType;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;

import static elegant.children.catchculture.common.exception.CustomException.*;

@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService loadUser 실행");

        /**
         * DefaultOAuth2UserService 객체를 생성하여, loadUser(userRequest)를 통해 DefaultOAuth2User 객체를 생성 후 반환
         * DefaultOAuth2UserService의 loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서
         * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환한다.
         * 결과적으로, OAuth2User는 OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저
         */

        final OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        final OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * userRequest에서 registrationId 추출 후 registrationId으로 SocialType 저장
         * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
         * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
         */

        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId: {}", registrationId);
        final SocialType socialType = SocialType.of(registrationId);
        log.info("socialType: {}", socialType);
        // OAuth2 로그인 시 키(PK)가 되는 값
        final String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)
        final Map<String, Object> attributes = oAuth2User.getAttributes();

        // socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        final OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        final User user = getUser(extractAttributes, socialType);


        return new CustomOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority(user.getRole().name())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                user.getEmail(),
                user.getRole());
    }

    /**
     * SocialType과 attributes에 들어있는 소셜 로그인의 식별값 id를 통해 회원을 찾아 반환하는 메소드
     * 만약 찾은 회원이 있다면, 그대로 반환하고 없다면 saveUser()를 호출하여 회원을 저장한다.
     */
    private User getUser(OAuthAttributes attributes, SocialType socialType) {
        final String email = attributes.getOAuth2UserInfo().getEmail();
        final User user = userRepository.findByEmail(email).orElse(null);
        log.info("user: {}", user);

        if(user != null) {
            if (user.getEmail().equals(email) && !user.getSocialType().equals(socialType)) {
                // TODO: 2023-10-11 다중 접속 처리 필요 이메일 같으면 토큰만 발급
                throw new AlreadyEmailExistException(ErrorCode.EMAIL_DUPLICATION);
            }
            return user;
        }else  {
            return saveUser(attributes, socialType);
        }

    }
    /**
     * OAuthAttributes의 toEntity() 메소드를 통해 빌더로 User 객체 생성 후 반환
     * 생성된 User 객체를 DB에 저장 : socialType, socialId, email, role 값만 있는 상태
     */
    @Transactional
    public User saveUser(OAuthAttributes attributes, SocialType socialType) {
        final User user = attributes.toEntity(socialType, attributes.getOAuth2UserInfo());
        return userRepository.save(user);
    }
}
