package elegant.children.catchculture.dto.admin.response;

import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventReportResponseDTO {

    private int id;
    private int userId;
    private String nickname;
    private CulturalEventDetail culturalEventDetail;
}
