package elegant.children.catchculture.service.user;

import elegant.children.catchculture.common.constant.Classification;
import elegant.children.catchculture.common.utils.RedisUtils;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.entity.user.SocialType;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventQueryRepository;
import elegant.children.catchculture.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final int userId = 1;
    private final String email = "test@gmail.com";
    private final String nickname = "test";
    private final SocialType socialType = SocialType.GOOGLE;

    @InjectMocks
    private UserService target;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CulturalEventQueryRepository culturalEventQueryRepository;

    @Mock
    private RedisUtils redisUtils;


    private User getUser() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .id(userId)
                .socialType(socialType)
                .build();
    }

    @Test
    void findByEmailTest() {

        final User testUser = getUser();

        doReturn(Optional.of(testUser)).when(userRepository).findByEmail(any(String.class));

        final User user = target.findByEmail(email).get();

        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getNickname(), user.getNickname());
        assertEquals(testUser.getId(), user.getId());
        assertEquals(testUser.getSocialType(), user.getSocialType());

        Mockito.verify(userRepository, times(1)).findByEmail(any(String.class));


    }

    @Test
    void findByEmailWithNullTest() {

        doReturn(Optional.empty()).when(userRepository).findByEmail(any(String.class));

        final Optional<User> empty = target.findByEmail(email);

        Assertions.assertThat(empty).isEmpty();
        Assertions.assertThatThrownBy(empty::get).isInstanceOf(NoSuchElementException.class);

        Mockito.verify(userRepository, times(1)).findByEmail(any(String.class));

    }

    @Test
    void saveUserTest() {
        final User testUser = getUser();

        doReturn(testUser).when(userRepository).save(any(User.class));

        final User user = target.saveUser(testUser);

        Assertions.assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
        Assertions.assertThat(user.getNickname()).isEqualTo(testUser.getNickname());
        Assertions.assertThat(user.getId()).isEqualTo(testUser.getId());
        Assertions.assertThat(user.getSocialType()).isEqualTo(testUser.getSocialType());

        Mockito.verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getCulturalEventListWithUser() {
        final PageImpl<CulturalEventListResponseDTO> inputResult = new PageImpl<>(List.of(
                CulturalEventListResponseDTO.builder().build(),
                CulturalEventListResponseDTO.builder().build(),
                CulturalEventListResponseDTO.builder().build()
        ));

        doReturn(
                inputResult
        ).when(culturalEventQueryRepository).getCulturalEventResponseDTOWithUser(any(List.class), any(Integer.class),
                any(Pageable.class), any(Classification.class));

        final Page<CulturalEventListResponseDTO> culturalEventListWithUser = target
                .getCulturalEventListWithUser(getUser(), 0, List.of(), Classification.LIKE);


        assertEquals(inputResult.getTotalElements(), culturalEventListWithUser.getTotalElements());
        assertEquals(inputResult.getTotalPages(), culturalEventListWithUser.getTotalPages());
        assertEquals(inputResult.getContent().size(), culturalEventListWithUser.getContent().size());

        verify(culturalEventQueryRepository, times(1)).getCulturalEventResponseDTOWithUser(any(List.class), any(Integer.class),
                any(Pageable.class), any(Classification.class));
    }

    @Test
    void logoutTest() {


        final MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        final MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

        target.logout(mockHttpServletRequest, mockHttpServletResponse, getUser());

    }











}