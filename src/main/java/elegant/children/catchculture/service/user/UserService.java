package elegant.children.catchculture.service.user;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventQueryRepository;
import elegant.children.catchculture.repository.culturalEvent.PartitionType;
import elegant.children.catchculture.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final CulturalEventQueryRepository culturalEventQueryRepository;

    @Transactional
    public void updateUserNickname(final User user) {
        userRepository.updateNickname(user.getNickname(), user.getId());
    }

//    public Page<CulturalEventListResponseDTO> getCulturalEventListWithUser(final User user,
//                                                                           final int offset,
//                                                                           final PartitionType partitionType) {
//        return culturalEventQueryRepository.getCulturalEventListWithUser(userId, categoryId, page, size, sortType);
//    }




}
