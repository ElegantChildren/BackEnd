package elegant.children.catchculture.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import elegant.children.catchculture.common.filter.CustomCookieSettingFilter;
import elegant.children.catchculture.common.filter.JwtAuthenticationFilter;
import elegant.children.catchculture.common.security.JwtTokenProvider;
import elegant.children.catchculture.common.security.oauth2.CustomOAuth2UserService;
import elegant.children.catchculture.repository.UserRepository;
import elegant.children.catchculture.common.security.oauth2.handler.OAuth2LoginFailureHandler;
import elegant.children.catchculture.common.security.oauth2.handler.OAuth2LoginSuccessHandler;
import elegant.children.catchculture.common.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtils redisUtils;
    private final ObjectMapper objectMapper;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable())
                .formLogin(formLoginConfigurer -> formLoginConfigurer.disable())
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequestsConfigurer ->
                        authorizeRequestsConfigurer.anyRequest().permitAll())
                .addFilterBefore(customCookieSettingFilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login(oauth2 -> {
            oauth2.loginPage("/oauth2/authorization/**");
            oauth2.successHandler(oAuth2LoginSuccessHandler());
            oauth2.failureHandler(oAuth2LoginFailureHandler());
            oauth2.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService()));

        });

        return http.build();
    }

    @Bean
    public CustomCookieSettingFilter customCookieSettingFilter() {
        return new CustomCookieSettingFilter();
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, userRepository, redisUtils);
    }

    @Bean
    public CustomOAuth2UserService customOAuth2UserService() {
        return new CustomOAuth2UserService(userRepository);
    }

    @Bean
    public AuthenticationFailureHandler oAuth2LoginFailureHandler() {
        return new OAuth2LoginFailureHandler(objectMapper);
    }

    @Bean
    public AuthenticationSuccessHandler oAuth2LoginSuccessHandler() {
        return new OAuth2LoginSuccessHandler(jwtTokenProvider, redisUtils);
    }


}
