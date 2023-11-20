package elegant.children.catchculture.common.converter;

import elegant.children.catchculture.repository.culturalEvent.PartitionType;
import org.springframework.core.convert.converter.Converter;

public class StringToSortConverter implements Converter<String, PartitionType> {

    @Override
    public PartitionType convert(String source) {
        return PartitionType.of(source.trim().toUpperCase());
    }
}
