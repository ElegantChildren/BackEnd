package elegant.children.catchculture.service.culturalEvent;

import elegant.children.catchculture.entity.culturalevent.CulturalEvent;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CulturalEventServiceTest {


    @Autowired
    EntityManager em;
    @Autowired
    CulturalEventService culturalEventService;

    @Test
    void getCulturalEventDetails() {
        // given
        // when

        // then
    }

}