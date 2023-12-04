package elegant.children.catchculture.dto.culturalEvent.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import elegant.children.catchculture.entity.culturalevent.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CulturalEventReportDTO implements Serializable {
    @NotNull
    private int reportId;
    @NotNull
    private String eventName;
    @NotNull
    private String eventLocation;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private Boolean isFree;
    private Category category;
    @NotNull
    private String description;
    private String snsAddress;
    private String phoneNumber;
//    private List<MultipartFile> fileList;
//    private List<String> storedFileUrl;
    private String wayToCome;

}