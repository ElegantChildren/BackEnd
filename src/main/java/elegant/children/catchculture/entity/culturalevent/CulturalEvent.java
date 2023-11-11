package elegant.children.catchculture.entity.culturalevent;

import elegant.children.catchculture.entity.visitauth.VisitAuth;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CulturalEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private CulturalEventDetail culturalEventDetail;

    private int viewCount;

    @Column(columnDefinition = "GEOMETRY")
    private Point geography;
    //ex) 만 8세 이상
    private LocalDateTime createdAt;

    public static Point createGeography(final Double longitude, final Double latitude) {
        if(longitude.equals(-200D) && latitude.equals(-200D)) {
            return null;
        }

        final String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        final Point point;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return point;
    }

    public void addViewCount() {
        this.viewCount++;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0;
    }
}
