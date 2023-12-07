package elegant.children.catchculture.repository.review;

import elegant.children.catchculture.entity.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review as r where r.culturalEvent.id = :culturalEventId and r.user.id = :userId and r.isDeleted = false")
    Optional<Review> findByCulturalEventIdAndUserId(@Param("culturalEventId") int culturalEventId, @Param("userId") int userId);

    @Modifying(clearAutomatically = true)
    @Query("update Review r set r.description = :description where r.id =:id and r.isDeleted = false")
    void updateReviewDescription(@Param("id") int id, @Param("description") String description);

    @Query("select r from Review r where r.user.id= :userId and r.isDeleted = false order by r.createdAt desc")
    Page<Review> findByUserIdOrderByCreatedAtDesc(int userId, Pageable pageable);

    @Query("select r from Review r where r.user.id = :userId and r.isDeleted = false")
    List<Review> findByUserId(int userId);
}
