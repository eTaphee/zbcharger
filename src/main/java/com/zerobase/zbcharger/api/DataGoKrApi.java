package com.zerobase.zbcharger.api;

import com.zerobase.zbcharger.domain.charger.dto.ChargerInfoResponse;
import com.zerobase.zbcharger.domain.charger.dto.ChargerStatusResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

/**
 * 공공 데이터 포털 proxy 인터페이스
 */
public interface DataGoKrApi {

    /**
     * 충전소 정보 조회
     *
     * @param serviceKey 서비스 키
     * @param pageNo     페이지 번호
     * @param numOfRows  한 페이지 결과 수
     * @return 충전소 정보 응답
     */
    @GetExchange("/B552584/EvCharger/getChargerInfo?dataType=JSON")
    ChargerInfoResponse getChargerInformation(@RequestParam("serviceKey") String serviceKey,
        @RequestParam("pageNo") int pageNo, @RequestParam("numOfRows") int numOfRows)
        throws RuntimeException;

    /**
     * 충전기 상태 조회
     *
     * @param serviceKey 서비스 키
     * @param pageNo     페이지 번호
     * @param numOfRows  한 페이지 결과 수
     * @return 충전기 상태 응답
     */
    @GetExchange("/B552584/EvCharger/getChargerStatus?dataType=JSON&period=5")
    ChargerStatusResponse getChargerStatus(@RequestParam("serviceKey") String serviceKey,
        @RequestParam("pageNo") int pageNo, @RequestParam("numOfRows") int numOfRows)
        throws RuntimeException;
}
