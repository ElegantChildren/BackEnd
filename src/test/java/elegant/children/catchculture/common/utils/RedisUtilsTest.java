package elegant.children.catchculture.common.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisUtilsTest {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void redisTest() {
        final String key = "test1";
        final String value = "백승진";


    }

}