package elegant.children.catchculture.repository.interaction;


import com.querydsl.jpa.impl.JPAQueryFactory;
import elegant.children.catchculture.entity.interaction.LikeStar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static elegant.children.catchculture.entity.interaction.QInteraction.interaction;

@Repository
@Slf4j
@RequiredArgsConstructor
public class InteractionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean existByStarAndUserIdAndCulturalEventId(final int userId, final int culturalEventId) {
        return queryFactory.selectOne()
                .from(interaction)
                .where(
                        interaction.user.id.eq(userId),
                        interaction.culturalEvent.id.eq(culturalEventId),
                        interaction.likeStar.eq(LikeStar.STAR)
                )
                .fetchFirst() != null;
    }

    public boolean existByLikeAndUserIdAndCulturalEventId(final int userId, final int culturalEventId) {
        return queryFactory.selectOne()
                .from(interaction)
                .where(
                        interaction.user.id.eq(userId),
                        interaction.culturalEvent.id.eq(culturalEventId),
                        interaction.likeStar.eq(LikeStar.LIKE)
                )
                .fetchFirst() != null;
    }
}
