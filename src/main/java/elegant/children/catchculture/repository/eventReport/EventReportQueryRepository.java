package elegant.children.catchculture.repository.eventReport;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import elegant.children.catchculture.dto.admin.response.AdminEventReportResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static elegant.children.catchculture.entity.eventreport.QEventReport.*;
import static elegant.children.catchculture.entity.user.QUser.*;

@Repository
@RequiredArgsConstructor
public class EventReportQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final int PAGE_SIZE = 10;

    public Slice<AdminEventReportResponseDTO> getEventReportList(final int lastId) {

        List<AdminEventReportResponseDTO> content =
                queryFactory.select(Projections.fields(AdminEventReportResponseDTO.class,
                eventReport.id,
                eventReport.user.id.as("userId"),
                user.nickname,
                eventReport.culturalEventDetail
                ))
                .from(eventReport)
                .innerJoin(user)
                .on(eventReport.user.id.eq(user.id))
                .where(
                        eventReport.id.loe(lastId)
                ).limit(PAGE_SIZE + 1)
                .orderBy(eventReport.id.desc())
                .fetch();

        boolean hasNext = false;
        if (content.size() == PAGE_SIZE + 1) {
            content.remove(PAGE_SIZE);
            hasNext = true;
        }

        return new SliceImpl<>(content, PageRequest.ofSize(PAGE_SIZE), hasNext);

    }
}
