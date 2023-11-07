package elegant.children.catchculture.controller;

import elegant.children.catchculture.common.utils.RegistrationUtils;
import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import elegant.children.catchculture.repository.CulturalEventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CulturalEventRegistrationController {

    private final CulturalEventRepository culturalEventRepository;

    @Value("${openapi.key}")
    private String openApiKey;


    @GetMapping("/0-999")
    public void registration0to1000() {
        final RestTemplate restTemplate = new RestTemplate();
        final List<CulturalEvent> culturalEventList = new ArrayList<>();
        final HashMap<String, Object> result = restTemplate.getForObject(RegistrationUtils.getOpenApiUrl(openApiKey, 0, 999), HashMap.class);
        addCulturalEventToList(culturalEventList, result);
        culturalEventRepository.saveAll(culturalEventList);
    }

    @GetMapping("/1000-1999")
    public void registration1000to2000() {
        final RestTemplate restTemplate = new RestTemplate();
        final List<CulturalEvent> culturalEventList = new ArrayList<>();
        final HashMap<String, Object> result = restTemplate.getForObject(RegistrationUtils.getOpenApiUrl(openApiKey, 1000, 1999), HashMap.class);
        addCulturalEventToList(culturalEventList, result);
        culturalEventRepository.saveAll(culturalEventList);
    }

    @GetMapping("/2000-2999")
    public void registration2000to3000() {
        final RestTemplate restTemplate = new RestTemplate();
        final List<CulturalEvent> culturalEventList = new ArrayList<>();
        final HashMap<String, Object> result = restTemplate.getForObject(RegistrationUtils.getOpenApiUrl(openApiKey, 2000, 2999), HashMap.class);
        addCulturalEventToList(culturalEventList, result);
        culturalEventRepository.saveAll(culturalEventList);
    }

    @GetMapping("/3000-3999")
    public void registration3000to4000() {
        final RestTemplate restTemplate = new RestTemplate();
        final List<CulturalEvent> culturalEventList = new ArrayList<>();
        final HashMap<String, Object> result = restTemplate.getForObject(RegistrationUtils.getOpenApiUrl(openApiKey, 3000, 3999), HashMap.class);
        addCulturalEventToList(culturalEventList, result);
        culturalEventRepository.saveAll(culturalEventList);
    }





    private void addCulturalEventToList(final List<CulturalEvent> culturalEventList, final HashMap<String, Object> result) {
        if(RegistrationUtils.isResultSuccess(result)) {
            RegistrationUtils.getEventInfo(result).forEach(event -> {
                    final CulturalEventDetail culturalEventDetail = RegistrationUtils.createCulturalEventDetail(event);
                    culturalEventList.add(CulturalEvent.builder()
                            .culturalEventDetail(culturalEventDetail)
                            .build());

//                    if(RegistrationUtils.isThisSungDong(event)) {
//                        final CulturalEventDetail culturalEventDetail = RegistrationUtils.createCulturalEventDetail(event);
//                        culturalEventList.add(CulturalEvent.builder()
//                                .culturalEventDetail(culturalEventDetail)
//                                .build());
//                    }
            });
        }
    }


}
