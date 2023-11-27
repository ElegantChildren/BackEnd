package elegant.children.catchculture.dto.admin.response;

import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminEventReportResponseDTO {

    private int id;
    private int userId;
    private String nickname;
    private CulturalEventDetail culturalEventDetail;
}
