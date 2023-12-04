package elegant.children.catchculture.service.user;

import elegant.children.catchculture.common.constant.Classification;
import elegant.children.catchculture.common.utils.CookieUtils;
import elegant.children.catchculture.common.utils.RedisUtils;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.entity.culturalevent.Category;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.event.CreatePointHistoryEvent;
import elegant.children.catchculture.event.CreateCulturalEvent;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventQueryRepository;
import elegant.children.catchculture.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final RedisUtils redisUtils;

    @Transactional
    @EventListener
    public void handleCreateCulturalEvent(final CreateCulturalEvent createCulturalEvent) {
        log.info("handleCreateCulturalEvent");
        updateUserPoint(createCulturalEvent.getUser(), createCulturalEvent.getPointChange().getPoint());
    }

    @Transactional
    @EventListener
    public void handleCreatePointHistoryEvent(final CreatePointHistoryEvent createPointHistoryEvent) {
        log.info("handleCreatePointHistoryEvent");
        updateUserPoint(createPointHistoryEvent.getUser(), createPointHistoryEvent.getPointChange().getPoint());
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
        redisUtils.deleteData(user.getEmail());
    }
}
