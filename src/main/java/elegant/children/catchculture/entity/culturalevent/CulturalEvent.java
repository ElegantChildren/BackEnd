package elegant.children.catchculture.entity.culturalevent;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CulturalEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cultural_event_id")
    private int id;


//    private Point point; 위도,경도
    @Embedded
    private CulturalEventDetail culturalEventDetail;

    private int viewCount;
    private int likeCount;
    //ex) 만 8세 이상

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0;
        this.likeCount = 0;
    }
}
