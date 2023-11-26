package elegant.children.catchculture.repository.pointHistory;

import elegant.children.catchculture.entity.pointhistory.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Integer> {
}
