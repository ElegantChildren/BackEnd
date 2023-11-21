package elegant.children.catchculture.repository.review;

import elegant.children.catchculture.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review as r where r.id = :culturalEventId and r.user.id = :userId")
    Optional<Review> findByCulturalEventIdAndUserId(@Param("culturalEventId") int culturalEventId, @Param("userId") int userId);
}
