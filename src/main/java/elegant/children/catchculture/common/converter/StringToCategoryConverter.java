package elegant.children.catchculture.common.converter;

import elegant.children.catchculture.entity.culturalevent.Category;
import org.springframework.core.convert.converter.Converter;


public class StringToCategoryConverter implements Converter<String, Category> {

        @Override
        public Category convert(String source) {
            return Category.of(source);
        }

}
