package elegant.children.catchculture.entity.culturalevent;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CulturalEventDetail implements Serializable {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String storedFileURL;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String place;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String reservationLink;

    private String wayToCome;
    private String sns;
    private String telephone;
    private Boolean isFree;


}
