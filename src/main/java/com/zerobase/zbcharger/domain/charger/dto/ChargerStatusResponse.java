package com.zerobase.zbcharger.domain.charger.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 충전기 상태 응답
 */
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ChargerStatusResponse {

    /**
     * 결과 코드
     */
    private final String resultCode;

    /**
     * 결과 메시지
     */
    @JsonProperty("resultMsg")
    private final String resultMessage;

    /**
     * 전체 건수
     */
    private final int totalCount;

    /**
     * 페이지 번호
     */
    private final int pageNo;

    /**
     * 한 페이지 결과 수
     */
    private final int numOfRows;

    /**
     * 충전기 상태
     */
    @JsonIgnore
    private List<ChargerStatus> items;

    @JsonProperty("items")
    private void unpackItemsFromNestedObject(Map<String, List<ChargerStatus>> object) {
        items = object.get("item");
    }
}
