package elegant.children.catchculture.service.pointHistory;

import elegant.children.catchculture.entity.pointhistory.PointHistory;
import elegant.children.catchculture.repository.pointHistory.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointGradeService {
    private final PointHistoryRepository pointHistoryRepository;

    public String calculateUserRank(int userId) {
//        LocalDateTime threeMonthsAgo = LocalDateTime.now().minusMonths(3);
        List<PointHistory> recentPositivePoints = pointHistoryRepository.findRecentPositivePointsByUserId(userId);

        int totalPositivePoints = recentPositivePoints.stream().mapToInt(PointHistory::getPointChange).sum();

        if (totalPositivePoints >= 10000) {
            return "Red";
        } else if (totalPositivePoints >= 6000) {
            return "Pink";
        } else if (totalPositivePoints >= 3000) {
            return "Yellow";
        } else {
            return "Green";
        }
    }
}
