package elegant.children.catchculture.entity.pointhistory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PointChange {

    REVIEW("리뷰 작성", 50),
    VISIT_AUTH("방문 인증", 50),
    COFFEE("커피 구매", -5000),
    IMO("이모티콘 구매", -3000);

    private final String description;
    private final int point;


}
