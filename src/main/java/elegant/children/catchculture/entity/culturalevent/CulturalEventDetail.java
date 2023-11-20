package elegant.children.catchculture.entity.culturalevent;

import com.fasterxml.jackson.annotation.JsonFormat;
import elegant.children.catchculture.common.converter.StoredFileUrlConverter;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class CulturalEventDetail implements Serializable {

    @Column(nullable = false, columnDefinition = "TEXT")
    @Convert(converter = StoredFileUrlConverter.class)
    private List<String> storedFileUrl;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
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
