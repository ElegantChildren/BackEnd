package elegant.children.catchculture.repository.culturalEvent;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventDetailsResponseDTO;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventListResponseDTO;
import elegant.children.catchculture.entity.culturalevent.Category;
import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import elegant.children.catchculture.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static elegant.children.catchculture.entity.culturalevent.QCulturalEvent.*;
import static elegant.children.catchculture.entity.visitauth.QVisitAuth.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CulturalEventQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CulturalEventDetailsResponseDTO getCulturalEventDetails(final int culturalEventId, final int userId) {
        return queryFactory.select(Projections.fields(CulturalEventDetailsResponseDTO.class,
                        culturalEvent.culturalEventDetail,
                        visitAuth.isAuthenticated))
                .from(culturalEvent)
                .leftJoin(visitAuth)
                .on(
                        culturalEvent.id.eq(visitAuth.culturalEvent.id),
                        visitAuth.user.id.eq(userId))
                .where(
                        culturalEvent.id.eq(culturalEventId)
                )
                .fetchOne();
    }

    public List<CulturalEventListResponseDTO> getCulturalEventList(final List<Category> categoryList, final Pageable pageable, final SortType sortType) {

        final LocalDateTime now = LocalDateTime.now();

        return queryFactory.select(Projections.fields(
                CulturalEventListResponseDTO.class,
                culturalEvent.id.as("culturalEventId"),
                culturalEvent.culturalEventDetail.title,
                culturalEvent.culturalEventDetail.startDate,
                culturalEvent.culturalEventDetail.endDate,
                culturalEvent.culturalEventDetail.place,
                culturalEvent.culturalEventDetail.storedFileURL.as("storedFileURL"),
                culturalEvent.likeCount,
                culturalEvent.viewCount,
                        Expressions.numberTemplate(Integer.class, "function('datediff', {0}, {1})",
                                culturalEvent.culturalEventDetail.endDate,
                                now).as("remainDay")
                ))
                .from(culturalEvent)
                .where(
                        notFinishedCulturalEvent(now),
                        categoryIn(categoryList)

                )
                .orderBy(
                        getSortType(sortType)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


    }


    private BooleanExpression categoryIn(final List<Category> categoryList) {
        log.info("categoryList = {}", categoryList);
        return categoryList == null || categoryList.isEmpty() ? null : culturalEvent.culturalEventDetail.category.in(categoryList);
    }

    private BooleanExpression notFinishedCulturalEvent(final LocalDateTime now) {
        return culturalEvent.culturalEventDetail.endDate.after(now);
    }

    private OrderSpecifier[] getSortType(final SortType sortType) {

        List<OrderSpecifier> orderSpecifier = new ArrayList<>();
        orderSpecifier.add(new OrderSpecifier<>(Order.ASC, culturalEvent.culturalEventDetail.startDate));
        switch (sortType) {
            case VIEW_COUNT:
                orderSpecifier.add(new OrderSpecifier<>(Order.DESC, culturalEvent.viewCount));
            case LIKE:
                orderSpecifier.add(new OrderSpecifier<>(Order.DESC, culturalEvent.likeCount));
            case RECENT:
                orderSpecifier.add(new OrderSpecifier<>(Order.ASC, culturalEvent.culturalEventDetail.endDate));

        }
        return orderSpecifier.toArray(new OrderSpecifier[orderSpecifier.size()]);
    }




}
