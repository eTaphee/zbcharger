package com.zerobase.zbcharger.domain.charger.dto;

public record UpdateChargerRequest(
    String chargerType,
    String location,
    String output,
    String method
) {

}
