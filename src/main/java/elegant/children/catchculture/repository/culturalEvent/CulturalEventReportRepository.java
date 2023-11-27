package elegant.children.catchculture.repository.culturalEvent;

import elegant.children.catchculture.dto.culturalEvent.response.CulturalEventReportDTO;
import elegant.children.catchculture.entity.eventreport.EventReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalEventReportRepository extends JpaRepository<EventReport, Long> {

}
