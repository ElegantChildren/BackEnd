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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
                        visitAuth.isAuthenticated.as("isAuthenticated")))
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

    public Page<CulturalEventListResponseDTO> getCulturalEventList(final List<Category> categoryList, final Pageable pageable, final PartitionType sortType) {

        final LocalDateTime now = LocalDateTime.now();

        final List<CulturalEventListResponseDTO> content = queryFactory.select(Projections.constructor(
                        CulturalEventListResponseDTO.class,
                        culturalEvent.id,
                        culturalEvent.culturalEventDetail,
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
        log.info("content : {}", content);

        final long count = queryFactory
                .select(culturalEvent.count())
                .from(culturalEvent)
                .where(
                        notFinishedCulturalEvent(now),
                        categoryIn(categoryList)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, count);


    }


    private BooleanExpression categoryIn(final List<Category> categoryList) {
        return categoryList == null || categoryList.isEmpty() ? null : culturalEvent.culturalEventDetail.category.in(categoryList);
    }

    private BooleanExpression notFinishedCulturalEvent(final LocalDateTime now) {
        return culturalEvent.culturalEventDetail.endDate.after(now);
    }

    private OrderSpecifier[] getSortType(final PartitionType sortType) {

        List<OrderSpecifier> orderSpecifier = new ArrayList<>();
        switch (sortType) {
            case VIEW_COUNT:
                orderSpecifier.add(new OrderSpecifier<>(Order.DESC, culturalEvent.viewCount));
                startDateASC(orderSpecifier);
            case LIKE:
                orderSpecifier.add(new OrderSpecifier<>(Order.DESC, culturalEvent.likeCount));
                startDateASC(orderSpecifier);
            case RECENT:
                startDateASC(orderSpecifier);
                orderSpecifier.add(new OrderSpecifier<>(Order.ASC, culturalEvent.culturalEventDetail.endDate));

        }
        return orderSpecifier.toArray(new OrderSpecifier[orderSpecifier.size()]);
    }

    private static void startDateASC(final List<OrderSpecifier> orderSpecifier) {
        orderSpecifier.add(new OrderSpecifier<>(Order.ASC, culturalEvent.culturalEventDetail.startDate));
    }


    public Page<CulturalEventListResponseDTO> getCulturalEventListWithCondition(final String keyword, final Pageable pageable, final PartitionType sortType) {

        final LocalDateTime now = LocalDateTime.now();

        final List<CulturalEventListResponseDTO> content = queryFactory.select(Projections.fields(
                        CulturalEventListResponseDTO.class,
                        culturalEvent.id,
                        culturalEvent.culturalEventDetail,
                        culturalEvent.likeCount,
                        culturalEvent.viewCount,
                        Expressions.numberTemplate(Integer.class, "function('datediff', {0}, {1})",
                                culturalEvent.culturalEventDetail.endDate,
                                now).as("remainDay")
                ))
                .from(culturalEvent)
                .where(
                        notFinishedCulturalEvent(now),
                        titleContains(keyword)
                ).orderBy(
                        getSortType(sortType)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        final long count = queryFactory
                .select(culturalEvent.count())
                .from(culturalEvent)
                .where(
                        notFinishedCulturalEvent(now),
                        titleContains(keyword)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, count);

    }

//    public Page<CulturalEventListResponseDTO> getCulturalEventResponseDTOWithUser(final List<Category> categoryList, final int userId,
//                                                                                  final Pageable pageable, final PartitionType partitionType) {
//
//        final LocalDateTime now = LocalDateTime.now();
//        final List<CulturalEventListResponseDTO> content;
//        if(partitionType.equals(PartitionType.LIKE) || partitionType.equals(PartitionType.STAR)) {
//            content = queryFactory.select(Projections.constructor(
//                    CulturalEventListResponseDTO.class,
//                    culturalEvent.id,
//                    culturalEvent.culturalEventDetail,
//                    culturalEvent.likeCount,
//                    culturalEvent.viewCount,
//                    Expressions.numberTemplate(Integer.class, "function('datediff', {0}, {1})",
//                            culturalEvent.culturalEventDetail.endDate,
//                            now).as("remainDay")
//            ))
//                    .from(culturalEvent)
//                    .leftJoin(visitAuth)
//                    .on(
//                            userIdEq(userId)
//                    )
//                    .where(
//                            notFinishedCulturalEvent(now),
//                            categoryIn(categoryList),
//                            visitAuth.isAuthenticated.eq(true)
//                    )
//                    .orderBy(
//                            getSortType(partitionType)
//                    )
//                    .offset(pageable.getOffset())
//                    .limit(pageable.getPageSize())
//                    .fetch();
//        }
//
//    }

    private BooleanExpression titleContains(final String keyword) {
        return keyword == null ? null : culturalEvent.culturalEventDetail.title.contains(keyword);
    }

    public BooleanExpression userIdEq(final int userId) {
        return userId == 0 ? null : visitAuth.user.id.eq(userId);
    }
}
