package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class StringLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {

        String text = parser.getText();

        if (Objects.isNull(text) || text.isEmpty()) {
            return null;
        }

        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
