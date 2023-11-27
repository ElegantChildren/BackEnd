package elegant.children.catchculture.service.culturalEvent;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventDetailsResponseDTO;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.entity.culturalevent.Category;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventQueryRepository;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventRepository;
import elegant.children.catchculture.common.constant.SortType;
import elegant.children.catchculture.repository.interaction.InteractionQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CulturalEventService {

    private final CulturalEventQueryRepository culturalEventQueryRepository;
    private final CulturalEventRepository culturalEventRepository;
    private final InteractionQueryRepository interactionQueryRepository;

    public List<CulturalEventListResponseDTO> getCulturalEventMainList() {

        return culturalEventQueryRepository.getCulturalEventMainList(culturalEventRepository.getCulturalEventMainIdList());

    }

    @Transactional
    public CulturalEventDetailsResponseDTO getCulturalEventDetails(final int culturalEventId, final User user) {

        final CulturalEventDetailsResponseDTO culturalEventDetails = culturalEventQueryRepository.getCulturalEventDetails(culturalEventId, user.getId());
        culturalEventDetails.setLikeAndBookmark(interactionQueryRepository.existByLikeAndUserIdAndCulturalEventId(user.getId(), culturalEventId),
                                                interactionQueryRepository.existByStarAndUserIdAndCulturalEventId(user.getId(), culturalEventId));
        culturalEventRepository.updateViewCount(culturalEventId);
        log.info("culturalEventDetails = {}", culturalEventDetails);

        return culturalEventDetails;
    }


    public String getCulturalEventTitle(final int culturalEventId, final User user) {

        final CulturalEventDetailsResponseDTO culturalEventDetails = culturalEventQueryRepository.getCulturalEventDetails(culturalEventId, user.getId());
//        log.info("culturalEventDetails = {}", culturalEventDetails);

        return culturalEventDetails.getCulturalEventDetail().getTitle();
    }


    public Page<CulturalEventListResponseDTO> getCulturalEventList(final List<Category> category, final int offset, final SortType sortType) {
        return culturalEventQueryRepository.getCulturalEventList(category, createPageRequest(offset), sortType);

    }

    public Page<CulturalEventListResponseDTO> searchCulturalEventListWithCondition(final String keyword, final int offset, final SortType sortType) {
        return culturalEventQueryRepository.getCulturalEventListWithCondition(keyword, createPageRequest(offset), sortType);
    }

    public static PageRequest createPageRequest(int offset) {
        return PageRequest.of(offset, 8);
    }
}
