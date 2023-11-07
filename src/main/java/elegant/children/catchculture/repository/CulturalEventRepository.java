package elegant.children.catchculture.repository;

import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalEventRepository extends JpaRepository<CulturalEvent, Integer> {

}
