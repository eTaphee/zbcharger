package com.zerobase.zbcharger.configuration.datagokr;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zerobase.zbcharger.api.DataGoKrApi;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringAreaCodeDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringAreaDetailCodeDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringBooleanDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringChargeMethodDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringChargerStatDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringLocalDateTimeDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringStationKindCodeDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringStationKindDetailCodeDeserializer;
import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.charger.type.ChargeMethod;
import com.zerobase.zbcharger.domain.charger.type.ChargerStat;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class DataGoKrApiConfiguration {

    private static final int MAX_IN_MEMORY_SIZE = 10 * 1024 * 1024; // 10 MB

    @Value("${data-go-kr.baseurl}")
    private String baseUrl;

    private final ObjectMapper objectMapper = webClientMapper();

    @Bean
    public DataGoKrApi dataGoKrApi() {
        WebClient webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
            .codecs(configurer -> {
                configurer.defaultCodecs().maxInMemorySize(MAX_IN_MEMORY_SIZE);
                configurer.defaultCodecs()
                    .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                configurer.defaultCodecs()
                    .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
            })
            .build();

        return HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient))
            .build()
            .createClient(DataGoKrApi.class);
    }

    /**
     * data.go.kr 응답을 처리하기 위한 objectMapper
     */
    @Bean
    public ObjectMapper webClientMapper() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringDeserializer());
        module.addDeserializer(AreaCode.class, new StringAreaCodeDeserializer());
        module.addDeserializer(AreaDetailCode.class, new StringAreaDetailCodeDeserializer());
        module.addDeserializer(boolean.class, new StringBooleanDeserializer());
        module.addDeserializer(ChargeMethod.class, new StringChargeMethodDeserializer());
        module.addDeserializer(ChargerStat.class, new StringChargerStatDeserializer());
        module.addDeserializer(LocalDateTime.class, new StringLocalDateTimeDeserializer());
        module.addDeserializer(StationKindCode.class, new StringStationKindCodeDeserializer());
        module.addDeserializer(StationKindDetailCode.class,
            new StringStationKindDetailCodeDeserializer());

        return new ObjectMapper()
            .registerModule(module)
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
