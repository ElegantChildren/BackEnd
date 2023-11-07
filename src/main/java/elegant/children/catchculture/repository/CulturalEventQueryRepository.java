package elegant.children.catchculture.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CulturalEventQueryRepository {

    private final JPAQueryFactory queryFactory;

}
