package elegant.children.catchculture.repository;

import elegant.children.catchculture.entity.visitauth.VisitAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitAuthRepository extends JpaRepository<VisitAuth, Integer> {

    @Query("select va.isAuthenticated from VisitAuth as va where va.user.id = :userId and va.culturalEvent.id = :culturalEventId")
    Optional<Boolean> isAuthenticated(final int userId, final int culturalEventId);
}
