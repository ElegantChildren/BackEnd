package elegant.children.catchculture.service.user;

import elegant.children.catchculture.common.constant.Classification;
import elegant.children.catchculture.common.utils.CookieUtils;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.entity.culturalevent.Category;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.event.CreatePointHistoryEvent;
import elegant.children.catchculture.event.CreateCulturalEvent;
import elegant.children.catchculture.event.SignOutEvent;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventQueryRepository;
import elegant.children.catchculture.repository.user.UserRepository;
import elegant.children.catchculture.service.GCS.GCSService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static elegant.children.catchculture.service.culturalEvent.CulturalEventService.createPageRequest;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    @Value("${jwt.token.header}")
    private String cookieName;

    private final UserRepository userRepository;
    private final CulturalEventQueryRepository culturalEventQueryRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final GCSService gcsService;

    @Transactional
    @EventListener
    @CachePut(value = "user", key = "#createCulturalEvent.user.email")
    public User handleCreateCulturalEvent(final CreateCulturalEvent createCulturalEvent) {
        final User user = userRepository.findByEmail(createCulturalEvent.getUser().getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.updatePoint(createCulturalEvent.getPointChange().getPoint());
        return user;
    }

    @Transactional
    @EventListener
    @CachePut(value = "user", key = "#createPointHistoryEvent.user.email")
    public User handleCreatePointHistoryEvent(final CreatePointHistoryEvent createPointHistoryEvent) {
        log.info("handleCreatePointHistoryEvent");
        final User user = userRepository.findByEmail(createPointHistoryEvent.getUser().getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        user.updatePoint(createPointHistoryEvent.getPointChange().getPoint());
        return user;
    }


    @Cacheable(value = "user", key = "#email")
    public Optional<User> findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User saveUser(final User user) {
        return userRepository.save(user);
    }


    @Transactional
    @CachePut(value = "user", key = "#user.email")
    public User updateUserNickname(final User user, final String nickName) {

        userRepository.updateNickname(nickName, user.getId());
        user.updateNickname(nickName);
        return user;
    }

    @Transactional
    @CachePut(value = "user", key = "#user.email")
    public User updateUserProfileImage(final User user, final MultipartFile file) {
        String storedFileUrl = gcsService.uploadImage(file);
        userRepository.updateProfileImage(storedFileUrl, user.getId());
        user.updateProfileImage(storedFileUrl);
        userRepository.flush();
        return user;
    }

    public Page<CulturalEventListResponseDTO> getCulturalEventListWithUser(final User user,
                                                                           final int offset,
                                                                           final List<Category> categoryList,final Classification classification) {
        return culturalEventQueryRepository.getCulturalEventResponseDTOWithUser( categoryList, user.getId(),
                                                                                createPageRequest(offset), classification);
    }


    private void updateUserPoint(final User user, final int point) {
        userRepository.updateUserPoint(user.getId(), user.getPoint() + point);
    }


    @CacheEvict(value = "user", key = "#user.email")
    public void logout(HttpServletRequest request, HttpServletResponse response, final User user) {
        CookieUtils.deleteCookie(request, response, cookieName);
    }

    @Transactional
    @CacheEvict(value = "user", key = "#user.email")
    public void singOut(HttpServletRequest request, HttpServletResponse response, final User user) {
        applicationEventPublisher.publishEvent(new SignOutEvent(user.getId()));
        logout(request, response, user);
        userRepository.deleteById(user.getId());
    }
}
