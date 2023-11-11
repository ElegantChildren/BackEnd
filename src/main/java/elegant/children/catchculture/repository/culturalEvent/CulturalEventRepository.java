package elegant.children.catchculture.repository.culturalEvent;

import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CulturalEventRepository extends JpaRepository<CulturalEvent, Integer> {


    @Query("select ce from CulturalEvent as ce left join fetch VisitAuth as va " +
            "on ce.id = va.culturalEvent.id and va.user.id = :userId where ce.id = :culturalEventId")
    Optional<CulturalEvent> findByUserIdAndCulturalEventIdWithVisitAuth(@Param("userId") int userId,
                                                                        @Param("culturalEventId") int culturalEventId);

    @Modifying(clearAutomatically = true)
    @Query("update CulturalEvent ce set ce.viewCount = ce.viewCount + 1 where ce.id = :culturalEventId")
    void updateViewCount(int culturalEventId);
}
