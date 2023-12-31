package elegant.children.catchculture.dto.culturalEvent.response;

import elegant.children.catchculture.entity.culturalevent.Category;
import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CulturalEventMapResponseDTO {

    private int culturalEventId;
    private String title;
    private String place;
    private String storedFileUrl;
    private Category category;
    private Double latitude;
    private Double longitude;
    private int likeCount;
    private int viewCount;

    private LocalDate startDate;
    private LocalDate endDate;
    private int remainDay;



    public CulturalEventMapResponseDTO(final CulturalEvent culturalEvent) {
        this.culturalEventId = culturalEvent.getId();
        this.title = culturalEvent.getCulturalEventDetail().getTitle();
        this.place = culturalEvent.getCulturalEventDetail().getPlace();
        this.storedFileUrl = culturalEvent.getCulturalEventDetail().getStoredFileUrl().get(0);
        this.latitude = culturalEvent.getCulturalEventDetail().getLatitude();
        this.longitude = culturalEvent.getCulturalEventDetail().getLongitude();
        this.category = culturalEvent.getCulturalEventDetail().getCategory();
        this.likeCount = culturalEvent.getLikeCount();
        this.viewCount = culturalEvent.getViewCount();
        this.startDate = culturalEvent.getCulturalEventDetail().getStartDate().toLocalDate();
        this.endDate = culturalEvent.getCulturalEventDetail().getEndDate().toLocalDate();

        final LocalDateTime now = LocalDateTime.now();
        remainDay = (int) ChronoUnit.DAYS.between(now.toLocalDate(),startDate);

        if(remainDay <= 0)
            this.remainDay = 0;
    }
}
