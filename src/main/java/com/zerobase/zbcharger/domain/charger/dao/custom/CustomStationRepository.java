package com.zerobase.zbcharger.domain.charger.dao.custom;

import com.zerobase.zbcharger.domain.charger.dto.StationDetail;
import com.zerobase.zbcharger.domain.charger.dto.SearchStationCondition;
import com.zerobase.zbcharger.domain.charger.dto.SearchStationSummaryCondition;
import com.zerobase.zbcharger.domain.charger.dto.StationSummary;
import com.zerobase.zbcharger.domain.charger.entity.Station;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomStationRepository {

    Page<Station> findAll(Pageable pageable, SearchStationCondition condition);

    Slice<StationSummary> findAllStationSummary(Pageable pageable,
        SearchStationSummaryCondition condition);

    Optional<StationDetail> findStationDetailById(String id);
}
