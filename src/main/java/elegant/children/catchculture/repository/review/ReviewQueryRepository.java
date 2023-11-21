package elegant.children.catchculture.repository.review;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import elegant.children.catchculture.dto.review.response.ReviewRatingResponseDTO;
import elegant.children.catchculture.dto.review.response.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static elegant.children.catchculture.entity.review.QReview.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;


    public ReviewRatingResponseDTO getReviewRating(final int culturalEventId) {
        final List<Tuple> result = queryFactory.select(review.rating, review.rating.count()).from(review)
                .where(
                        culturalEventIdEq(culturalEventId)
                )
                .groupBy(review.rating)
                .orderBy(review.rating.asc())
                .fetch();

        final ReviewRatingResponseDTO reviewRatingResponseDTO = new ReviewRatingResponseDTO();
        for (Tuple tuple : result) {
            int rating = tuple.get(0, Integer.class);
            long count = tuple.get(1, Long.class);
            switch (rating) {
                case 5:
                    reviewRatingResponseDTO.setCountFive(count);
                    break;
                case 4:
                    reviewRatingResponseDTO.setCountFour(count);
                    break;
                case 3:
                    reviewRatingResponseDTO.setCountThree(count);
                    break;
                case 2:
                    reviewRatingResponseDTO.setCountTwo(count);
                    break;
                case 1:
                    reviewRatingResponseDTO.setCountOne(count);
                    break;
            }
        }
        reviewRatingResponseDTO.setAvgRating();
        return reviewRatingResponseDTO;
    }

    private BooleanExpression culturalEventIdEq(final int culturalEventId) {
        return review.culturalEvent.id.eq(culturalEventId);
    }

    public List<ReviewResponseDTO> getReviewList(final int culturalEventId) {
        return queryFactory.select(Projections.fields(ReviewResponseDTO.class,
                review.id,
                review.rating,
                review.description,
                review.createdAt,
                review.user.id,
                review.user.nickname,
                review.storedFileURL.as("userProfileUrl")
        ))
                .leftJoin(review.user)
                .from(review)
                .where(
                        culturalEventIdEq(culturalEventId)
                )
                .orderBy(review.createdAt.desc())
                .fetch();

    }
}
