package com.zerobase.zbcharger.domain.charger.controller.client;

import com.zerobase.zbcharger.configuration.security.annotation.RoleUser;
import com.zerobase.zbcharger.domain.charger.dto.client.SearchStationSummaryCondition;
import com.zerobase.zbcharger.domain.charger.dto.client.StationSummary;
import com.zerobase.zbcharger.domain.charger.service.client.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자 충전소 API 컨트롤러
 */
@RoleUser
@RestController("ClientStationController")
@RequestMapping("/client/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    /**
     * 충전소 요약 목록 조회
     *
     * @param pageable  페이징 정보
     * @param condition 검색 조건
     * @return 충전소 요약 목록
     */
    @GetMapping("/summary")
    public Slice<StationSummary> searchStationSummaryList(Pageable pageable,
        SearchStationSummaryCondition condition) {
        return stationService.searchStationSummaryList(pageable, condition);
    }
}
