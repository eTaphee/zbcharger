package com.zerobase.zbcharger.domain.charger.dao.custom;

import com.zerobase.zbcharger.domain.charger.dto.ChargerSummary;
import com.zerobase.zbcharger.domain.charger.dto.SearchChargerSummaryCondition;
import java.util.List;

public interface CustomChargerRepository {

    List<ChargerSummary> findAllChargeSummary(SearchChargerSummaryCondition condition);
}
