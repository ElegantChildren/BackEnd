package elegant.children.catchculture.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import elegant.children.catchculture.common.converter.StringToCategoryConverter;
import elegant.children.catchculture.common.converter.StringToSortConverter;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


     @Bean
     public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
         return new JPAQueryFactory(entityManager);
     }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSortConverter());
        registry.addConverter(new StringToCategoryConverter());
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
