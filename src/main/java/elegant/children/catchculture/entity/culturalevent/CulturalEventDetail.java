package elegant.children.catchculture.entity.culturalevent;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

@Embeddable
public class CulturalEventDetail {

    private String storedFileName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String title;
    private String description;
    private String place;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String orgSite;
    private int fee;
}
