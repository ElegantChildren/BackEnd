package elegant.children.catchculture.repository.culturalEvent;

import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CulturalEventRepository extends JpaRepository<CulturalEvent, Integer> {


    @Modifying(clearAutomatically = true)
    @Query("update CulturalEvent ce set ce.viewCount = ce.viewCount + 1 where ce.id = :culturalEventId")
    void updateViewCount(int culturalEventId);

    @Query("select ce from CulturalEvent ce where ce.culturalEventDetail.place = :place")
    CulturalEvent findByPlace(final String place);
}
