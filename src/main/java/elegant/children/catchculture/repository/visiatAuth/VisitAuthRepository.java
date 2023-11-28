package elegant.children.catchculture.repository.visiatAuth;

import elegant.children.catchculture.entity.visitauth.VisitAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitAuthRepository extends JpaRepository<VisitAuth, Integer> {

    @Query("select va.isAuthenticated from VisitAuth as va where va.user.id = :userId and va.culturalEvent.id = :culturalEventId")
    Optional<Boolean> isAuthenticated(final int userId, final int culturalEventId);

    @Query("select va from VisitAuth as va join fetch User as u join fetch CulturalEvent as ce " +
            "where va.id = :id and u.id = :userId and va.culturalEvent.id = :culturalEventId")
    Optional<VisitAuth> findByIdWithUserAAndCulturalEvent(final int id, final int userId, final int culturalEventId);
}
