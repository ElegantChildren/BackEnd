package elegant.children.catchculture.dto.culturalEvent.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import elegant.children.catchculture.entity.culturalevent.Category;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CulturalEventDetailsResponseDTO implements Serializable {


    private String storedFileURL;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
    private String title;
    private String place;

    private Category category;

    private String description;
    private String reservationLink;

    private String wayToCome;
    private String sns;
    private String telephone;
    private Boolean isFree;

    private boolean isAuthenticated;
    private boolean isLiked;
    private boolean isBookmarked;

    public void setLikeAndBookmark(final boolean isLiked, final boolean isBookmarked) {
        this.isLiked = isLiked;
        this.isBookmarked = isBookmarked;
    }

}
