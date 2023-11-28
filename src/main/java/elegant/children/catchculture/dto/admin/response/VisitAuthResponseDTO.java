package elegant.children.catchculture.dto.admin.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class VisitAuthResponseDTO {

    private int id;
    private int userId;
    private String nickname;
    private int culturalEventId;
    private String title;
    private String description;
    private List<String> storedFileUrl;

}
