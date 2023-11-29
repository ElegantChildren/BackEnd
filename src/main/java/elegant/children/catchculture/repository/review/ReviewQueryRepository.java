package elegant.children.catchculture.repository.review;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import elegant.children.catchculture.dto.review.response.ReviewRatingResponseDTO;
import elegant.children.catchculture.dto.review.response.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static elegant.children.catchculture.entity.review.QReview.*;
import static elegant.children.catchculture.entity.user.QUser.*;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final int PAGE_SIZE = 10;


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

    public Slice<ReviewResponseDTO> getReviewList(final int culturalEventId, final int userId, final int lastId) {

        List<ReviewResponseDTO> content = queryFactory.select(Projections.fields(ReviewResponseDTO.class,
                        review.id,
                        review.rating,
                        review.description,
                        review.createdAt,
                        review.user.nickname,
                        review.storedFileURL.as("userProfileUrl")
                ))
                .from(review)
                .innerJoin(user).on(review.user.id.eq(user.id))
                .where(
                        culturalEventIdEq(culturalEventId),
                        reviewUserIdNe(userId),
                        reviewIdLt(lastId)

                )
                .orderBy(review.id.desc())
                .limit(PAGE_SIZE+1)
                .fetch();

        boolean hasNext = false;

        if(content.size() == PAGE_SIZE + 1) {
            content.remove(PAGE_SIZE);
            hasNext = true;
        }
        return new SliceImpl<>(content, PageRequest.ofSize(PAGE_SIZE), hasNext);



    }

    private BooleanExpression culturalEventIdEq(final int culturalEventId) {
        return review.culturalEvent.id.eq(culturalEventId);
    }

    private BooleanExpression reviewIdLt(final int lastId) {
        if(lastId == 0) {
            return null;
        }

        return review.id.lt(lastId);
    }
    private BooleanExpression reviewUserIdNe(final int userId) {

        return review.user.id.ne(userId);
    }
}
