package elegant.children.catchculture.service.GeoCode;

import elegant.children.catchculture.dto.GeoCode.GeoCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class GeoCodeService {
    @Value("${google.geocoding.apiKey}")
    private String apiKey;

    @Value("${google.geocoding.endpoint}")
    private String apiEndpoint;

    public GeoCodeDTO geocoder(final String address) {

        String url = String.format("%s?address=%s&key=%s", apiEndpoint, address, apiKey);
        log.info(url);
        ResponseEntity<GeoCodeDTO> response = new RestTemplate().getForEntity(url, GeoCodeDTO.class);
        return response.getBody();
    }

}
