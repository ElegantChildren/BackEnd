package elegant.children.catchculture.repository.eventReport;

import elegant.children.catchculture.entity.eventreport.EventReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventReportRepository extends JpaRepository<EventReport, Integer> {

    @Query("select er from EventReport er join fetch User as u where er.id = :id and u.id = :userId")
    Optional<EventReport> findByUserId(final int id, final int userId);
}
