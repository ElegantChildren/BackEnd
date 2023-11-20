package elegant.children.catchculture.common.converter;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StoredFileUrlConverter implements AttributeConverter<List<String>, String> {

    private final String Delimiter = ", ";
    @Override
    public String convertToDatabaseColumn(final List<String> attribute) {
        return attribute.stream().collect(Collectors.joining(Delimiter));
    }

    @Override
    public List<String> convertToEntityAttribute(final String dbData) {
        return Arrays.asList(dbData.split(Delimiter));
    }
}
