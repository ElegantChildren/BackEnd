package elegant.children.catchculture.common.converter;

import elegant.children.catchculture.repository.culturalEvent.SortType;
import org.springframework.core.convert.converter.Converter;

public class StringToSortConverter implements Converter<String, SortType> {

    @Override
    public SortType convert(String source) {
        return SortType.of(source.trim().toUpperCase());
    }
}
