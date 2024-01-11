package com.zerobase.zbcharger.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.zbcharger.domain.charger.dto.ChargerInfoResponse;
import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.charger.type.ChargeMethod;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MappingTest {

    @Test
    void test() throws IOException {
        // given
        ObjectMapper mapper = new ObjectMapper().configure(
            com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false);

        // when
        ChargerInfoResponse response = null;
        try (FileInputStream fis = new FileInputStream("src/test/resources/data.json")) {
            response = mapper.readValue(fis.readAllBytes(), ChargerInfoResponse.class);
        }

        // then
        Assertions.assertEquals(10, response.getItems().size());
        Assertions.assertEquals(AreaCode.Z11, response.getItems().get(0).getAreaCode());
        Assertions.assertEquals(AreaDetailCode.Z11740, response.getItems().get(0).getAreaDetailCode());
        Assertions.assertEquals(StationKindCode.B0, response.getItems().get(0).getStationKindCode());
        Assertions.assertEquals(StationKindDetailCode.B001, response.getItems().get(0).getStationKindDetailCode());
        Assertions.assertEquals(ChargeMethod.SINGLE, response.getItems().get(0).getMethod());
    }
}
