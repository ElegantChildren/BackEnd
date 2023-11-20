package elegant.children.catchculture.dto.culturalEvent.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CulturalEventListResponseDTO {

    private int culturalEventId;

    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;

    private String place;
    private String storedFileUrl;
    private int likeCount;
    private int viewCount;

    private int remainDay;

    public CulturalEventListResponseDTO(final int culturalEventId, final CulturalEventDetail culturalEventDetail,
                                        final int likeCount, final  int viewCount,
                                        final int remainDay) {
        this.culturalEventId = culturalEventId;
        this.title = culturalEventDetail.getTitle();
        this.startDate = culturalEventDetail.getStartDate();
        this.endDate = culturalEventDetail.getEndDate();
        this.place = culturalEventDetail.getPlace();
        this.storedFileUrl = culturalEventDetail.getStoredFileUrl().get(0);
        this.likeCount = likeCount;
        this.viewCount = viewCount;
    }
}
