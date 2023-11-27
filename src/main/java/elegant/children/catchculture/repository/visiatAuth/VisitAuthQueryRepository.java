package elegant.children.catchculture.repository.visiatAuth;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import elegant.children.catchculture.dto.admin.response.AdminVisitAuthListResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static elegant.children.catchculture.entity.culturalevent.QCulturalEvent.*;
import static elegant.children.catchculture.entity.user.QUser.*;
import static elegant.children.catchculture.entity.visitauth.QVisitAuth.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class VisitAuthQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final int PAGE_SIZE = 10;

    public Slice<AdminVisitAuthListResponseDTO> getNotAuthenticatedVisitAuthList(final int lastId) {
        List<AdminVisitAuthListResponseDTO> content = queryFactory.select(Projections.fields(AdminVisitAuthListResponseDTO.class,
                        visitAuth.id,
                        user.id.as("userId"),
                        user.nickname,
                        culturalEvent.id.as("culturalEventId"),
                        culturalEvent.culturalEventDetail.title,
                        culturalEvent.culturalEventDetail.description,
                        visitAuth.storedFileUrl
                ))
                .from(visitAuth)
                .innerJoin(culturalEvent)
                .on(visitAuth.culturalEvent.id.eq(culturalEvent.id))
                .innerJoin(user)
                .on(visitAuth.user.id.eq(user.id))
                .where(
                        visitAuth.isAuthenticated.eq(false),
                        visitAuth.id.loe(lastId)
                )
                .orderBy(visitAuth.id.desc())
                .limit(PAGE_SIZE + 1)
                .fetch();

        boolean hasNext = false;

        if(content.size() == PAGE_SIZE + 1) {
            content.remove(PAGE_SIZE);
            hasNext = true;
        }
        return new SliceImpl<>(content, PageRequest.ofSize(PAGE_SIZE), hasNext);

    }
}
