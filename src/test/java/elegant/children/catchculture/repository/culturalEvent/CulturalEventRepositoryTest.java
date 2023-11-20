package elegant.children.catchculture.repository.culturalEvent;

import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import elegant.children.catchculture.entity.culturalevent.CulturalEventDetail;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CulturalEventRepositoryTest {

    @Autowired
    CulturalEventRepository culturalEventRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void converterTest() {
        // given
        final LocalDateTime startDate = LocalDateTime.of(2023, 11, 14, 0, 0, 0);
        final LocalDateTime endDate = startDate.plusDays(3);
        final List<String> storedFileUrl = List.of("url1", "url2");
        final CulturalEventDetail culturalEventDetail = CulturalEventDetail.builder()
                .title("title")
                .place("place")
                .storedFileUrl(storedFileUrl)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        final CulturalEvent culturalEvent = CulturalEvent.builder()
                .culturalEventDetail(culturalEventDetail)
                .build();
        culturalEventRepository.save(culturalEvent);
        entityManager.flush();
        // when
        final CulturalEvent findCulturalEvent = culturalEventRepository.findByPlace("place");
        // then
        assertEquals(findCulturalEvent.getCulturalEventDetail().getPlace(), "place");
        assertEquals(findCulturalEvent.getCulturalEventDetail().getStoredFileUrl(), storedFileUrl);
    }

}