package elegant.children.catchculture.dto.culturalEvent.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CulturalEventReportDTO implements Serializable {
    @NonNull
    private String eventName;
    @NonNull
    private String eventLocation;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private boolean isFree;
    @NonNull
    private String description;
    private String snsAddress;
    private String phoneNumber;
//    private List<MultipartFile> fileList;
//    private List<String> storedFileUrl;
    private String wayToCome;

}