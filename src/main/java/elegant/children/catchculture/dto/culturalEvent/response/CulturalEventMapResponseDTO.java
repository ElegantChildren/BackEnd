package elegant.children.catchculture.dto.culturalEvent.response;

import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CulturalEventMapResponseDTO {

    private int culturalEventId;
    private String title;
    private String place;
    private String storedFileUrl;
    private Double latitude;
    private Double longitude;
    private int likeCount;
    private int viewCount;

    public CulturalEventMapResponseDTO(final CulturalEvent culturalEvent) {
        this.culturalEventId = culturalEvent.getId();
        this.title = culturalEvent.getCulturalEventDetail().getTitle();
        this.place = culturalEvent.getCulturalEventDetail().getPlace();
        this.storedFileUrl = culturalEvent.getCulturalEventDetail().getStoredFileUrl().get(0);
        this.latitude = culturalEvent.getCulturalEventDetail().getLatitude();
        this.longitude = culturalEvent.getCulturalEventDetail().getLongitude();
        this.likeCount = culturalEvent.getLikeCount();
        this.viewCount = culturalEvent.getViewCount();
    }
}
