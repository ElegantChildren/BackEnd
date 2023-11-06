package elegant.children.catchculture.entity.culturalevent;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;


import java.time.LocalDateTime;

@Embeddable
public class CulturalEventDetail {

    @Column(nullable = false)
    private String storedFileName;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String place;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(columnDefinition = "GEOMETRY", nullable = false)
    private Point geography;

    private String reservationLink;
    private String wayToCome;
    private String sns;
    private String telephone;
    private Boolean isFree;

    public void setGeography(final Double longitude, final Double latitude) {
        if(longitude.equals(-200D) && latitude.equals(-200D)) {
            return;
        }

        final String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        final Point point;
        try {
            point = (Point) new WKTReader().read(pointWKT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.geography = point;
    }
}
