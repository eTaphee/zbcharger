package com.zerobase.zbcharger.domain.charger.dao.custom;

import com.zerobase.zbcharger.domain.charger.dto.admin.SearchCompanyRequest;
import com.zerobase.zbcharger.domain.charger.dto.client.SearchStationSummaryCondition;
import com.zerobase.zbcharger.domain.charger.dto.client.StationSummary;
import com.zerobase.zbcharger.domain.charger.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomCompanyRepository {

    Page<Company> findAll(Pageable pageable, SearchCompanyRequest request);

    Slice<StationSummary> findAllStationSummary(Pageable pageable,
        SearchStationSummaryCondition request);
}
