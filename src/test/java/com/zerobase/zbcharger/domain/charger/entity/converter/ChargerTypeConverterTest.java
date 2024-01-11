package com.zerobase.zbcharger.domain.charger.entity.converter;

import static com.zerobase.zbcharger.domain.charger.type.ChargerType.AC;
import static com.zerobase.zbcharger.domain.charger.type.ChargerType.AC3;
import static com.zerobase.zbcharger.domain.charger.type.ChargerType.CHA;
import static com.zerobase.zbcharger.domain.charger.type.ChargerType.DC;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zerobase.zbcharger.domain.charger.type.ChargerType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ChargerTypeConverterTest {

    @ParameterizedTest
    @DisplayName("convertToDatabaseColumn 테스트")
    @MethodSource("chargerTypeSetAndValueProvider")
    void convertToDatabaseColumn(ChargerTypeSetAndValueAssertion assertion) {
        // given
        ChargerTypeConverter converter = new ChargerTypeConverter();

        // when
        Integer value = converter.convertToDatabaseColumn(assertion.set());

        // then
        assertEquals(assertion.value, value);
    }

    @ParameterizedTest
    @DisplayName("convertToEntityAttribute 테스트")
    @MethodSource("chargerTypeSetAndValueProvider")
    void convertToEntityAttribute(ChargerTypeSetAndValueAssertion assertion) {
        // given
        ChargerTypeConverter converter = new ChargerTypeConverter();

        // when
        Set<ChargerType> chargerTypes = converter.convertToEntityAttribute(assertion.value);

        // then
        assertEquals(assertion.set, chargerTypes);
    }

    public static Stream<Arguments> chargerTypeSetAndValueProvider() {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(
            new ChargerTypeSetAndValueAssertion(EnumSet.noneOf(ChargerType.class), 0)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC), 1)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC3), 2)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC, AC3), 3)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(CHA), 4)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC, CHA), 5)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC3, CHA), 6)));
        arguments.add(
            Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC, AC3, CHA), 7)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(DC), 8)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC, DC), 9)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC3, DC), 10)));
        arguments.add(
            Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC, AC3, DC), 11)));
        arguments.add(Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(CHA, DC), 12)));
        arguments.add(
            Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC, CHA, DC), 13)));
        arguments.add(
            Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC3, CHA, DC), 14)));
        arguments.add(
            Arguments.of(new ChargerTypeSetAndValueAssertion(EnumSet.of(AC, AC3, CHA, DC), 15)));
        return arguments.stream();
    }

    record ChargerTypeSetAndValueAssertion(EnumSet<ChargerType> set, Integer value) {

    }
}
