package elegant.children.catchculture.service.pointHistory;

import elegant.children.catchculture.dto.user.PointUsageResponseDTO;
import elegant.children.catchculture.repository.pointHistory.PointUsageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointUsageService {

    private final PointUsageRepository pointUsageRepository;

    public List<PointUsageResponseDTO> findPointUsage(){

        return pointUsageRepository.findPointUsages();
    }




}
