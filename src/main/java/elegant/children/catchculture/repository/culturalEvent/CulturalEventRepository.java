package elegant.children.catchculture.repository.culturalEvent;

import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface CulturalEventRepository extends JpaRepository<CulturalEvent, Integer> {


    Optional<CulturalEvent> findById(final int culturalEventId);

    @Query("select * from CulturalEvent ce where ce.culturalEventDetail.endDate <= :now")
    List<CulturalEvent> findAllByEndDateBefore(final LocalDateTime now);

    @Modifying(clearAutomatically = true)
    @Query("update CulturalEvent ce set ce.viewCount = ce.viewCount + 1 where ce.id = :culturalEventId")
    void updateViewCount(int culturalEventId);

    @Query("select ce from CulturalEvent ce where ce.culturalEventDetail.place = :place")
    CulturalEvent findByPlace(final String place);

    @Query(value = """
        select x.id from (select id, title, place, row_number() over (partition by category order by start_date) as n from cultural_event
        where end_date >= date(now())) as x where x.n <= 1;
    """, nativeQuery = true)
    List<Integer> getCulturalEventMainIdList();
}
