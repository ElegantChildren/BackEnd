package elegant.children.catchculture.repository.pointHistory;

import elegant.children.catchculture.dto.user.PointHistoryResponseDTO;
import elegant.children.catchculture.entity.pointhistory.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Integer> {

    @Query("select new elegant.children.catchculture.dto.user.PointHistoryResponseDTO(p.id,p.createdAt,p.description,p.pointChange, p.user.id) from PointHistory p where p.user.id = :user_id order by p.createdAt desc")
    Page<PointHistoryResponseDTO> findHistories(int user_id, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("delete from PointHistory p where p.user.id = :userId")
    void deleteByUserId(int userId);

    @Query("SELECT ph FROM PointHistory ph WHERE ph.user.id = :userId AND ph.pointChange > 0")
    List<PointHistory> findRecentPositivePointsByUserId(int userId);
}
