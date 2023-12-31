package elegant.children.catchculture.repository.culturalEvent;

import elegant.children.catchculture.entity.eventreport.EventReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalEventReportRepository extends JpaRepository<EventReport, Long> {

    Page<EventReport> findByUserIdOrderByCreatedAtDesc(int userId, Pageable pageable);

}
