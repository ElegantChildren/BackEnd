package elegant.children.catchculture.dto.culturalEvent.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import elegant.children.catchculture.entity.culturalevent.Category;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CulturalEventReportDTO implements Serializable {
    private Category category;
    private String reservationLink;
    private String eventName;
    private String eventLocation;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private boolean isFree;
    private String description;
    private String snsAddress;
    private String phoneNumber;
//    private List<MultipartFile> fileList;
//    private List<String> storedFileUrl;
    private String wayToCome;

}