package elegant.children.catchculture.service.culturalEvent;

import elegant.children.catchculture.common.constant.SortType;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventDetailsResponseDTO;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.entity.culturalevent.Category;
import elegant.children.catchculture.entity.user.User;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventQueryRepository;
import elegant.children.catchculture.repository.culturalEvent.CulturalEventRepository;
import elegant.children.catchculture.repository.interaction.InteractionQueryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CulturalEventServiceTest {

    @InjectMocks
    private CulturalEventService target;

    @Mock
    private CulturalEventQueryRepository culturalEventQueryRepository;

    @Mock
    private CulturalEventRepository culturalEventRepository;

    @Mock
    private InteractionQueryRepository interactionQueryRepository;


    @Test
    void getCulturalEventMainListTest() {

        final List<Integer> integers = List.of(1, 2, 3);
        doReturn(integers).when(culturalEventRepository).getCulturalEventMainIdList();

        final List<CulturalEventListResponseDTO> result = List.of(
                CulturalEventListResponseDTO.builder()
                        .culturalEventId(1)
                        .build(),
                CulturalEventListResponseDTO.builder()
                        .culturalEventId(2)
                        .build(),
                CulturalEventListResponseDTO.builder()
                        .culturalEventId(3)
                        .build()
        );
        doReturn(result).when(culturalEventQueryRepository).getCulturalEventMainList(any(List.class));

        final ArgumentCaptor<List<Integer>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        final List<CulturalEventListResponseDTO> targetResult = target.getCulturalEventMainList();

        BDDMockito.then(culturalEventQueryRepository).should(times(1)).getCulturalEventMainList(argumentCaptor.capture());

        Assertions.assertThat(targetResult.size()).isEqualTo(result.size());
        Assertions.assertThat(targetResult.get(0).getCulturalEventId()).isEqualTo(result.get(0).getCulturalEventId());
        Assertions.assertThat(targetResult.get(1).getCulturalEventId()).isEqualTo(result.get(1).getCulturalEventId());
        Assertions.assertThat(targetResult.get(2).getCulturalEventId()).isEqualTo(result.get(2).getCulturalEventId());

        Assertions.assertThat(argumentCaptor.getValue().size()).isEqualTo(3);
        Assertions.assertThat(argumentCaptor.getValue()).isEqualTo(integers);

        verify(culturalEventRepository, times(1)).getCulturalEventMainIdList();
        verify(culturalEventQueryRepository, times(1)).getCulturalEventMainList(any(List.class));

    }

    @Test
    void getCulturalEventDetailsTest() {

        final User user = User.builder()
                .id(1)
                .build();

        final CulturalEventDetailsResponseDTO culturalEventListResponseDTO = CulturalEventDetailsResponseDTO.builder().isAuthenticated(true).build();

        doReturn(culturalEventListResponseDTO).when(culturalEventQueryRepository).getCulturalEventDetails(any(Integer.class), any(Integer.class));

        doReturn(true).when(interactionQueryRepository).existByLikeAndUserIdAndCulturalEventId(any(Integer.class), any(Integer.class));
        doReturn(true).when(interactionQueryRepository).existByStarAndUserIdAndCulturalEventId(any(Integer.class), any(Integer.class));
        doNothing().when(culturalEventRepository).updateViewCount(any(Integer.class));


        final int culturalEventId = 1;
        final CulturalEventDetailsResponseDTO targetResult = target.getCulturalEventDetails(culturalEventId, user);
        final ArgumentCaptor<Integer> argumentCaptorForLikeUserId = ArgumentCaptor.forClass(Integer.class);
        final ArgumentCaptor<Integer> argumentCaptorForLikeCulturalEventId = ArgumentCaptor.forClass(Integer.class);

        final ArgumentCaptor<Integer> argumentCaptorForStarUserId = ArgumentCaptor.forClass(Integer.class);
        final ArgumentCaptor<Integer> argumentCaptorForStarCulturalEventId = ArgumentCaptor.forClass(Integer.class);

        BDDMockito.then(interactionQueryRepository).should(times(1)).existByLikeAndUserIdAndCulturalEventId(argumentCaptorForLikeUserId.capture(), argumentCaptorForLikeCulturalEventId.capture());
        BDDMockito.then(interactionQueryRepository).should(times(1)).existByStarAndUserIdAndCulturalEventId(argumentCaptorForStarUserId.capture(), argumentCaptorForStarCulturalEventId.capture());


        Assertions.assertThat(argumentCaptorForLikeUserId.getValue()).isEqualTo(user.getId());
        Assertions.assertThat(argumentCaptorForLikeCulturalEventId.getValue()).isEqualTo(culturalEventId);

        Assertions.assertThat(argumentCaptorForStarUserId.getValue()).isEqualTo(user.getId());
        Assertions.assertThat(argumentCaptorForStarCulturalEventId.getValue()).isEqualTo(culturalEventId);

        Assertions.assertThat(targetResult.isLiked()).isTrue();
        Assertions.assertThat(targetResult.isBookmarked()).isTrue();
        Assertions.assertThat(targetResult.isAuthenticated()).isTrue();

        verify(culturalEventQueryRepository, times(1)).getCulturalEventDetails(any(Integer.class), any(Integer.class));
        verify(interactionQueryRepository, times(1)).existByLikeAndUserIdAndCulturalEventId(any(Integer.class), any(Integer.class));
        verify(interactionQueryRepository, times(1)).existByStarAndUserIdAndCulturalEventId(any(Integer.class), any(Integer.class));
        verify(culturalEventRepository, times(1)).updateViewCount(any(Integer.class));

    }

    @Test
    void getCulturalEventListTest() {
        final List<Category> categoryList = List.of(Category.MOVIE, Category.EDUCATION_EXPERIENCE);
        final int offset = 0;
        final SortType sortType = SortType.VIEW_COUNT;


        final PageImpl<CulturalEventListResponseDTO> pageResult = new PageImpl<>(List.of(
                CulturalEventListResponseDTO.builder()
                        .build(),
                CulturalEventListResponseDTO.builder()
                        .build(),
                CulturalEventListResponseDTO.builder()
                        .build()
        ));

        doReturn(pageResult).when(culturalEventQueryRepository).getCulturalEventList(any(List.class), any(PageRequest.class), any(SortType.class));

        final ArgumentCaptor<List<Category>> argumentCaptorForCategory = ArgumentCaptor.forClass(List.class);
        final ArgumentCaptor<SortType> argumentCaptorForSortType = ArgumentCaptor.forClass(SortType.class);


        final Page<CulturalEventListResponseDTO> targetResult = target.getCulturalEventList(categoryList, offset, sortType);

        BDDMockito.then(culturalEventQueryRepository).should(times(1)).getCulturalEventList(argumentCaptorForCategory.capture(), any(PageRequest.class), argumentCaptorForSortType.capture());

        Assertions.assertThat(argumentCaptorForCategory.getValue()).isEqualTo(categoryList);
        Assertions.assertThat(argumentCaptorForSortType.getValue()).isEqualTo(sortType);

        Assertions.assertThat(targetResult.getTotalElements()).isEqualTo(pageResult.getTotalElements());
        Assertions.assertThat(targetResult.getTotalPages()).isEqualTo(pageResult.getTotalPages());
        Assertions.assertThat(targetResult.getContent().size()).isEqualTo(pageResult.getContent().size());

        verify(culturalEventQueryRepository, times(1)).getCulturalEventList(any(List.class), any(PageRequest.class), any(SortType.class));

    }





}