package elegant.children.catchculture.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import elegant.children.catchculture.entity.culturalevent.QCulturalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CulturalEventQueryRepository {

    private final JPAQueryFactory queryFactory;

}
