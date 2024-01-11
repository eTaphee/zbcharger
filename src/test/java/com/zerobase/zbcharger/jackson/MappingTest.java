package com.zerobase.zbcharger.jackson;

import static com.zerobase.zbcharger.domain.charger.type.AreaCode.Z11;
import static com.zerobase.zbcharger.domain.charger.type.AreaDetailCode.Z11740;
import static com.zerobase.zbcharger.domain.charger.type.ChargeMethod.SINGLE;
import static com.zerobase.zbcharger.domain.charger.type.StationKindCode.B0;
import static com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode.B001;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.zbcharger.configuration.datagokr.DataGoKrApiConfiguration;
import com.zerobase.zbcharger.domain.charger.dto.ChargerInfoResponse;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.zerobase.zbcharger")
public class MappingTest {

    @Test
    void test() throws IOException {
        // given
        ObjectMapper mapper = new DataGoKrApiConfiguration().webClientMapper();

        // when
        ChargerInfoResponse response = null;
        try (FileInputStream fis = new FileInputStream("src/test/resources/data.json")) {
            response = mapper.readValue(fis.readAllBytes(), ChargerInfoResponse.class);
        }

        // then
        Assertions.assertEquals(10, response.getItems().size());
        Assertions.assertEquals(Z11, response.getItems().get(0).getAreaCode());
        Assertions.assertEquals(Z11740, response.getItems().get(0).getAreaDetailCode());
        Assertions.assertEquals(B0, response.getItems().get(0).getStationKindCode());
        Assertions.assertEquals(B001, response.getItems().get(0).getStationKindDetailCode());
        Assertions.assertEquals(SINGLE, response.getItems().get(0).getMethod());
    }
}
