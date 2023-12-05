package elegant.children.catchculture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케줄러 사용
public class CatchCultureApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatchCultureApplication.class, args);
	}

}
