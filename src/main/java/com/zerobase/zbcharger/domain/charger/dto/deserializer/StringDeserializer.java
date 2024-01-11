package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.springframework.util.StringUtils;

public class StringDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException {
        String text = parser.getText();
        text = (text == null) ? null : text.trim();

        if (StringUtils.hasText(text) && !"null".equalsIgnoreCase(text)) {
            return text;
        }

        return null;
    }

}
