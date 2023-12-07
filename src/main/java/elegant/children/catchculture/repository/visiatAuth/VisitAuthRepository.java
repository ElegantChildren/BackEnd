package elegant.children.catchculture.repository.visiatAuth;

import elegant.children.catchculture.entity.visitauth.VisitAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitAuthRepository extends JpaRepository<VisitAuth, Integer> {

    @Query("select va.isAuthenticated from VisitAuth as va where va.user.id = :userId and va.culturalEvent.id = :culturalEventId")
    Optional<Boolean> isAuthenticated(final int userId, final int culturalEventId);

    @Query("select va from VisitAuth va " +
            "join fetch va.user u " +
            "where va.id = :id and u.id = :userId and va.culturalEvent.id = :culturalEventId")
    Optional<VisitAuth> findByIdWithUserAndCulturalEvent(final @Param("id") int id, final @Param("userId") int userId, final @Param("culturalEventId") int culturalEventId);

    @Query("select va from VisitAuth as va " +
            "join fetch va.user as u " +
            "join fetch va.culturalEvent as ce " +
            "where u.id = :userId and ce.id = :culturalEventId")
    Optional<VisitAuth> findByUserAndCulturalEvent(final @Param("userId") int userId, final @Param("culturalEventId") int culturalEventId);

    @Modifying(clearAutomatically = true)
    @Query("delete from VisitAuth va where va.user.id = :userId")
    void deleteByUserId(int userId);

    @Query("select va from VisitAuth va where va.user.id = :userId")
    List<VisitAuth> findByUserId(int userId);

    @Query("select va from VisitAuth va where va.id = :visitAuthId")
    VisitAuth findById(int visitAuthId);
}
