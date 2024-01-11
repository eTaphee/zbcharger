package com.zerobase.zbcharger.domain.charger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerobase.zbcharger.domain.charger.entity.Charger;
import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.charger.type.ChargeMethod;
import com.zerobase.zbcharger.domain.charger.type.ChargerStat;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * 충전기 정보
 */
@EqualsAndHashCode(of = {"stationId", "chargerId"})
@Getter
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChargerInfo {

    /**
     * 충전소 명
     */
    @JsonProperty("statNm")
    private final String stationName;

    /**
     * 충전소 ID
     */
    @JsonProperty("statId")
    private final String stationId;

    /**
     * 충전기 ID
     */
    @JsonProperty("chgerId")
    private final String chargerId;

    /**
     * 충전기 타입
     */
    @JsonProperty("chgerType")
    private final String chargerType;

    /**
     * 주소
     */
    @JsonProperty("addr")
    private final String address;

    /**
     * 상세 위치
     */
    private final String location;

    /**
     * 위도
     */
    @JsonProperty("lat")
    private final Double latitude;

    /**
     * 경도
     */
    @JsonProperty("lng")
    private final Double longitude;

    /**
     * 이용 가능 시간
     */
    private final String useTime;

    /**
     * 기관 아이디
     */
    @JsonProperty("busiId")
    private final String companyId;

    /**
     * 기관 명
     */
    @JsonProperty("bnm")
    private final String companyName;

    /**
     * 운영기관 명
     */
    @JsonProperty("busiNm")
    private final String operatorName;

    /**
     * 운영기관 연락처
     */
    @JsonProperty("busiCall")
    private final String operatorCall;

    /**
     * 충전기 상태
     */
    private final ChargerStat stat;

    /**
     * 상태 갱신 일시
     */
    @JsonProperty("statUpdDt")
    private final LocalDateTime statUpdatedAt;

    /**
     * 마지막 충전 시작 일시
     */
    @JsonProperty("lastTsdt")
    private final LocalDateTime lastChargeStartedAt;

    /**
     * 마지막 충전 종료 일시
     */
    @JsonProperty("lastTedt")
    private final LocalDateTime lastChargeEndedAt;

    /**
     * 충전중 시작 일시
     */
    @JsonProperty("nowTsdt")
    private final LocalDateTime nowChargeStartedAt;

    /**
     * 충전 용량
     */
    private final Integer output;

    /**
     * 충전 방식
     */
    private final ChargeMethod method;

    /**
     * 지역 코드
     */
    @JsonProperty("zcode")
    private final AreaCode areaCode;

    /**
     * 지역 구분 상세코드
     */
    @JsonProperty("zscode")
    private final AreaDetailCode areaDetailCode;

    /**
     * 충전소 구분 코드
     */
    @JsonProperty("kind")
    private final StationKindCode stationKindCode;

    /**
     * 충전소 구분 상세코드
     */
    @JsonProperty("kindDetail")
    private final StationKindDetailCode stationKindDetailCode;

    /**
     * 주차료 무료 여부
     */
    @JsonProperty("parkingFree")
    private final boolean parkingFreeYn;

    /**
     * 충전소 안내
     */
    private final String note;

    /**
     * 이용자 제한
     */
    private final boolean limitYn;

    /**
     * 이용제한 사유
     */
    private final String limitDetail;

    /**
     * 삭제 여부
     */
    @JsonProperty("delYn")
    private final boolean deletedYn;

    /**
     * 삭제 사유
     */
    @JsonProperty("delDetail")
    private final String deleteDetail;

    /**
     * 편의 제공 여부
     */
    private final boolean trafficYn;

    public CompanyInfo getCompany() {
        return CompanyInfo.builder()
            .id(companyId)
            .name(companyName)
            .call(operatorCall)
            .operator(operatorName)
            .build();
    }

    public StationInfo getStation() {
        return StationInfo.builder()
            .id(stationId)
            .companyId(companyId)
            .name(stationName)
            .address(address)
            .location(location)
            .useTime(useTime)
            .areaCode(areaCode)
            .areaDetailCode(areaDetailCode)
            .stationKindCode(stationKindCode)
            .stationKindDetailCode(stationKindDetailCode)
            .parkingFreeYn(parkingFreeYn)
            .note(note)
            .limitYn(limitYn)
            .limitDetail(limitDetail)
            .trafficYn(trafficYn)
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }

    public Charger toEntity() {
        return Charger.builder()
            .id(stationId + chargerId)
            .stationId(stationId)
//            .chargerType(chargerType)
            .stat(stat)
            .output(output)
            .method(method)
            .statUpdatedAt(statUpdatedAt)
            .lastChargeStartedAt(lastChargeStartedAt)
            .lastChargeFinishedAt(lastChargeEndedAt)
            .nowChargeStartedAt(nowChargeStartedAt)
            .deletedYn(deletedYn)
            .deleteDetail(deleteDetail)
            .build();
    }
}
