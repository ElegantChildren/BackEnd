package elegant.children.catchculture.dto.culturalEvent.response;


import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CulturalEventDetailsResponseDTO implements Serializable {

    private CulturalEventDetail culturalEventDetail;

    private boolean isAuthenticated;
    private boolean isLiked;
    private boolean isBookmarked;

    private int likeCount;
    private int viewCount;

    public void setLikeAndBookmark(final boolean isLiked, final boolean isBookmarked) {
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;
    }

}
