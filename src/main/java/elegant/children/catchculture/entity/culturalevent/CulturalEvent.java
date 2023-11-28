package elegant.children.catchculture.entity.culturalevent;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class CulturalEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private CulturalEventDetail culturalEventDetail;

    private int viewCount;
    private int likeCount;

    //ex) 만 8세 이상
    private LocalDateTime createdAt;

    public void addLikeCount() {
        this.likeCount++;
    }

    public void minusLikeCount() {
        this.likeCount--;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0;
        this.likeCount = 0;
    }
}
