package com.zerobase.zbcharger.domain.charger.service.client;

import com.zerobase.zbcharger.domain.charger.dao.StationRepository;
import com.zerobase.zbcharger.domain.charger.dto.client.SearchStationSummaryCondition;
import com.zerobase.zbcharger.domain.charger.dto.client.StationSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

/**
 * 사용자 충전소 서비스
 */
@Service("ClientStationService")
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    /**
     * 충전소 요약 목록 조회
     * @param pageable 페이징 정보
     * @param condition 검색 조건
     * @return 충전소 요약 목록
     */
    public Slice<StationSummary> searchStationSummaryList(Pageable pageable,
        SearchStationSummaryCondition condition) {
        return stationRepository.findAllStationSummary(pageable, condition);
    }
}
