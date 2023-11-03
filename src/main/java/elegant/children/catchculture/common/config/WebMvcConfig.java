package elegant.children.catchculture.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


     @Bean
     public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
         return new JPAQueryFactory(entityManager);
     }
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry
//                .addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods(
//                        "GET",
//                        "POST",
//                        "PUT",
//                        "DELETE",
//                        "PATCH",
//                        "OPTIONS"
//                )
//                .allowedHeaders("*")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }

}
