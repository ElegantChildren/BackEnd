package elegant.children.catchculture.entity.review;

import elegant.children.catchculture.common.converter.StoredFileUrlConverter;
import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import elegant.children.catchculture.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StoredFileUrlConverter.class)
    private List<String> storedFileURL;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int rating;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cultural_event_id")
    private CulturalEvent culturalEvent;

    private boolean isDeleted;

    @PrePersist
    public void prePersist() {
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }

    public void delete() {
        this.isDeleted = true;
    }
}
