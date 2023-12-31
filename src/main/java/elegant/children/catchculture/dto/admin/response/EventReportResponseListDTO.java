package elegant.children.catchculture.dto.admin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventReportResponseListDTO {

    private int id;
    private String nickname;
    private String title;
    private LocalDateTime createdAt;

}
