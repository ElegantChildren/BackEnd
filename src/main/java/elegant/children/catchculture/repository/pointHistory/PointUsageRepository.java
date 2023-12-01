package elegant.children.catchculture.repository.pointHistory;

import elegant.children.catchculture.dto.user.PointUsageResponseDTO;
import elegant.children.catchculture.entity.pointhistory.PointUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointUsageRepository extends JpaRepository<PointUsage, Integer> {
    @Query("SELECT new elegant.children.catchculture.dto.user.PointUsageResponseDTO(p.id,p.name,p.photoUrl,p.price,p.description) FROM PointUsage p")
    List<PointUsageResponseDTO> findPointUsages();
}
