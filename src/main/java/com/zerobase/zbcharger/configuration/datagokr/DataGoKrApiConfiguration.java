package com.zerobase.zbcharger.configuration.datagokr;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.zerobase.zbcharger.api.DataGoKrApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class DataGoKrApiConfiguration {
    private static final int MAX_IN_MEMORY_SIZE = 10 * 1024 * 1024; // 10 MB

    @Value("${data-go-kr.baseurl}")
    private String baseUrl;

    @Bean
    public DataGoKrApi dataGoKrApi() {
        WebClient webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader(ACCEPT, APPLICATION_JSON_VALUE)
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(MAX_IN_MEMORY_SIZE))
            .build();

        return HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient))
            .build()
            .createClient(DataGoKrApi.class);
    }
}
