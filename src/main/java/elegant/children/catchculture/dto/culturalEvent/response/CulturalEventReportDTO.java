package elegant.children.catchculture.dto.culturalEvent.response;

import org.springframework.web.multipart.MultipartFile;

//import java.time.LocalDate;
import java.time.LocalDateTime;

public class CulturalEventReportDTO {
    private String eventName;
    private String eventLocation;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isFree;
    private String description;
    private String snsAddress;
    private String phoneNumber;
    private MultipartFile eventPhoto;
    private String directions;

}