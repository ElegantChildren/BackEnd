package elegant.children.catchculture.repository.culturalEvent;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventDetailsResponseDTO;
import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static elegant.children.catchculture.entity.culturalevent.QCulturalEvent.*;
import static elegant.children.catchculture.entity.visitauth.QVisitAuth.*;

@Repository
@RequiredArgsConstructor
public class CulturalEventQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CulturalEventDetailsResponseDTO getCulturalEventDetails(final int culturalEventId, final int userId) {

        return queryFactory.select(Projections.fields(CulturalEventDetailsResponseDTO.class, culturalEvent.culturalEventDetail, visitAuth.isAuthenticated))
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



}
