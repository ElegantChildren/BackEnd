package elegant.children.catchculture.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class VisitAuthRepositoryTest {

    @Autowired
    private VisitAuthRepository visitAuthRepository;

    @Test
    void isAuthenticatedTest() {
        Optional<Boolean> authenticated = visitAuthRepository.isAuthenticated(5, 1);

        System.out.println("authenticated = " + authenticated);
    }

}