package com.zerobase.zbcharger.domain.charger.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringAreaCodeDeserializer;
import com.zerobase.zbcharger.domain.charger.dto.deserializer.StringAreaDetailCodeDeserializer;
import com.zerobase.zbcharger.domain.charger.entity.Station;
import com.zerobase.zbcharger.domain.charger.type.AreaCode;
import com.zerobase.zbcharger.domain.charger.type.AreaDetailCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindCode;
import com.zerobase.zbcharger.domain.charger.type.StationKindDetailCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 충전소 추가 요청
 *
 * @param id                    충전소 아이디
 * @param companyId             충전소 아이디
 * @param name                  충전소 이름
 * @param address               충전소 주소
 * @param location              충전소 위치
 * @param useTime               충전소 이용시간
 * @param areaCode              충전소 지역코드
 * @param areaDetailCode        충전소 지역상세코드
 * @param stationKindCode       충전소 종류코드
 * @param stationKindDetailCode 충전소 종류상세코드
 * @param parkingFreeYn         주차 무료 여부
 * @param note                  비고
 * @param useLimitYn            사용 제한 여부
 * @param useLimitDetail        사용 제한 상세
 * @param trafficYn             편의 제공 여부
 * @param longitude             경도
 * @param latitude              위도
 */
public record AddStationRequest(
    @Size(min = 8, max = 8) String id,
    @Size(min = 2, max = 2) String companyId,
    @NotBlank String name,
    @NotBlank String address,
    String location,
    @NotBlank String useTime,
    @NotNull @JsonDeserialize(using = StringAreaCodeDeserializer.class) AreaCode areaCode,
    @NotNull @JsonDeserialize(using = StringAreaDetailCodeDeserializer.class) AreaDetailCode areaDetailCode,
    @NotNull StationKindCode stationKindCode,
    @NotNull StationKindDetailCode stationKindDetailCode,
    boolean parkingFreeYn,
    String note,
    boolean useLimitYn,
    String useLimitDetail,
    boolean trafficYn,
    Double longitude,
    Double latitude
) {

    public Station toEntity() {
        return Station.builder()
            .id(id)
            .companyId(companyId)
            .name(name)
            .address(address)
            .location(location)
            .useTime(useTime)
            .areaCode(areaCode)
            .areaDetailCode(areaDetailCode)
            .stationKindCode(stationKindCode)
            .stationKindDetailCode(stationKindDetailCode)
            .parkingFreeYn(parkingFreeYn)
            .note(note)
            .useLimitYn(useLimitYn)
            .useLimitDetail(useLimitDetail)
            .trafficYn(trafficYn)
            .longitude(longitude)
            .latitude(latitude)
            .build();
    }
}
