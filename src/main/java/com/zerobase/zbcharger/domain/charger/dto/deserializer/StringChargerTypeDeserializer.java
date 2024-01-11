package com.zerobase.zbcharger.domain.charger.dto.deserializer;

import static com.zerobase.zbcharger.domain.charger.type.ChargerType.AC;
import static com.zerobase.zbcharger.domain.charger.type.ChargerType.AC3;
import static com.zerobase.zbcharger.domain.charger.type.ChargerType.CHA;
import static com.zerobase.zbcharger.domain.charger.type.ChargerType.DC;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class StringChargerTypeDeserializer extends JsonDeserializer<Set<ChargerType>> {

    @Override
    public Set<ChargerType> deserialize(JsonParser parser, DeserializationContext ctxt)
        throws IOException {
        EnumSet<ChargerType> set = EnumSet.noneOf(ChargerType.class);
        String value = parser.getText();
        switch (value != null ? value : "") {
            case "01":
                set.add(CHA);
                break;
            case "02":
                set.add(AC);
                break;
            case "03":
                set.add(AC3);
                set.add(CHA);
                break;
            case "04", "08":
                set.add(DC);
                break;
            case "05":
                set.add(CHA);
                set.add(DC);
                break;
            case "06":
                set.add(AC3);
                set.add(CHA);
                set.add(DC);
                break;
            case "07":
                set.add(AC3);
                break;
        }

        return Collections.unmodifiableSet(set);
    }
}
