package elegant.children.catchculture.repository.review;

import elegant.children.catchculture.dto.review.response.ReviewDTO;
import elegant.children.catchculture.entity.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("select r from Review as r where r.culturalEvent.id = :culturalEventId and r.user.id = :userId")
    Optional<Review> findByCulturalEventIdAndUserId(@Param("culturalEventId") int culturalEventId, @Param("userId") int userId);


    @Modifying(clearAutomatically = true)
    @Query("update Review r set r.description = :description where r.id =:id")
    void updateReviewDescription(@Param("id") int id, @Param("description") String description);

    @Modifying(clearAutomatically = true)
    void deleteReviewById(int id);


//    @Query("select new elegant.children.catchculture.dto.review.response.ReviewDTO(r.id, r.user.nickname, r.createdAt, r.description, r.storedFileURL,r.rating, d.title, d.storedFileUrl) from Review r join r.culturalEvent.culturalEventDetail d where r.user.id = :user_id order by r.createdAt desc")
//    Page<ReviewDTO> getMyList(int user_id, Pageable pageable);
    Page<Review> findByUserIdOrderByCreatedAtDesc(int userId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("delete from Review r where r.user.id = :userId")
    void deleteByUserId(int userId);
}
