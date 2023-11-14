package elegant.children.catchculture.dto.culturalEvent.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CulturalEventListResponseDTO {

    private int culturalEventId;

    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;

    private String place;
    private String storedFileURL;
    private int likeCount;
    private int viewCount;

    private int remainDay;
}
