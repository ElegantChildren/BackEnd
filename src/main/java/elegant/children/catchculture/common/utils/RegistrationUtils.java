package elegant.children.catchculture.common.utils;

import elegant.children.catchculture.entity.culturalevent.Category;
import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class RegistrationUtils {

    private static final String OPEN_API_PREFIX = "http://openapi.seoul.go.kr:8088/";
    private static final String OPEN_API_SUFFIX = "/json/culturalEventInfo/";
    private static final String CODE = "INFO-000";

//    private static final String GUNAME = "GUNAME"; //구이름
    private static final String CODENAME = "CODENAME"; //카테고리
    private static final String TITLE = "TITLE"; //제목
    private static final String PLACE = "PLACE"; //장소
    private static final String IS_FREE = "IS_FREE"; //시간
    private static final String START_DATE = "STRTDATE"; //행사시작일
    private static final String END_DATE = "END_DATE"; //행사종료일
    private static final String LOT = "LOT"; //위도
    private static final String LAT = "LAT"; //경도
    private static final String MAIN_IMG = "MAIN_IMG"; //이미지
    private static final String ORG_LINK = "ORG_LINK"; //홈페이지

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String CULTURAL_EVENT = "축제";
//    private static final String SUNG_DONG = "성동구";
    private static final String FREE = "무료";




    public static String getOpenApiUrl(final String openApiKey, final int start, final int end) {
        return OPEN_API_PREFIX + openApiKey + OPEN_API_SUFFIX + start + "/" + end;
    }
    public static boolean isResultSuccess(final HashMap<String, Object> result) {

        return ((HashMap) ((HashMap) result.get("culturalEventInfo")).get("RESULT")).get("CODE").equals(CODE);
    }
    public static List<HashMap<String, Object>> getEventInfo(final HashMap<String, Object> result) {
        return (List<HashMap<String, Object>>) ((Map)result.get("culturalEventInfo")).get("row");
    }

//    public static boolean isThisSungDong(final HashMap<String, Object> event) {
//        return event.get(GUNAME).equals(SUNG_DONG);
//    }

    public static Category getCategory(final HashMap<String, Object> event) {
        final String category = (String) event.get(CODENAME);
        if(category.startsWith(CULTURAL_EVENT)){
            return Category.FESTIVAL;
        }
        return Category.of(category);
    }

    public static String getTitle(final HashMap<String, Object> event) {
        return (String) event.get(TITLE);
    }

    public static String getPlace(final HashMap<String, Object> event) {
        return (String) event.get(PLACE);
    }

    public static boolean getIsFree(final HashMap<String, Object> event) {
        return event.get(IS_FREE).equals(FREE);
    }

    public static LocalDateTime getStartDate(final HashMap<String, Object> event) {
        return LocalDateTime.parse(((String) event.get(START_DATE)).substring(0, 19), FORMATTER);
    }

    public static LocalDateTime getEndDate(final HashMap<String, Object> event) {
        return LocalDateTime.parse(((String) event.get(END_DATE)).substring(0, 19), FORMATTER);
    }

    public static Double getLot(final HashMap<String, Object> event) {
        final String lot = (String) event.get(LOT);
        return lot.isEmpty() ? -200D : Double.valueOf(lot);
    }

    public static Double getLat(final HashMap<String, Object> event) {
        final String lat = (String) event.get(LAT);
        return lat.isEmpty() ? -200D : Double.valueOf(lat);
    }

    public static List<String> getMainImg(final HashMap<String, Object> event) {
        return List.of(event.get(MAIN_IMG).toString());
    }

    public static String getOrgLink(final HashMap<String, Object> event) {
        return (String) event.get(ORG_LINK);
    }

    public static CulturalEvent createCulturalEvent(final HashMap<String, Object> event) {
        final CulturalEventDetail culturalEventDetail = CulturalEventDetail.builder()
                .storedFileUrl(getMainImg(event))
                .startDate(getStartDate(event))
                .endDate(getEndDate(event))
                .title(getTitle(event))
                .place(getPlace(event))
                .category(getCategory(event))
                .reservationLink(getOrgLink(event))
                .isFree(getIsFree(event))
                .build();

        return CulturalEvent.builder()
                .geography(CulturalEvent.createGeography(getLot(event), getLat(event)))
                .culturalEventDetail(culturalEventDetail)
                .build();
    }






}
