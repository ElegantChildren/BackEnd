package elegant.children.catchculture.repository.eventReport;

import elegant.children.catchculture.entity.eventreport.EventReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventReportRepository extends JpaRepository<EventReport, Integer> {

//    @Query("select er from EventReport er join fetch User as u where er.id = :id and u.id = :userId")
    @Query("select er from EventReport er join fetch er.user u where er.id = :id and u.id = :userId")
    Optional<EventReport> findByUserId(final @Param("id") int id, final @Param("userId") int userId);

    @Modifying(clearAutomatically = true)
    @Query("delete from EventReport er where er.user.id = :id")
    void deleteByUserId(int id);

    @Query("select er from EventReport er where er.user.id = :userId")
    List<EventReport> findByUserId(int userId);
}
