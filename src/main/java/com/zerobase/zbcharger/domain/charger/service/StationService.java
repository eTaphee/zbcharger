package com.zerobase.zbcharger.domain.charger.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.COMPANY_NOT_FOUND;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.STATION_ALREADY_DELETED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.STATION_ALREADY_EXISTS;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.STATION_NOT_FOUND;

import com.zerobase.zbcharger.domain.charger.dao.CompanyRepository;
import com.zerobase.zbcharger.domain.charger.dao.StationRepository;
import com.zerobase.zbcharger.domain.charger.dto.admin.AddStationRequest;
import com.zerobase.zbcharger.domain.charger.dto.admin.SearchStationRequest;
import com.zerobase.zbcharger.domain.charger.dto.admin.StationResponse;
import com.zerobase.zbcharger.domain.charger.dto.admin.UpdateStationRequest;
import com.zerobase.zbcharger.domain.charger.dto.client.SearchStationSummaryCondition;
import com.zerobase.zbcharger.domain.charger.dto.client.StationSummary;
import com.zerobase.zbcharger.domain.charger.entity.Station;
import com.zerobase.zbcharger.exception.CustomException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자용 충전소 서비스
 */
@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;
    private final CompanyRepository companyRepository;

    /**
     * 충전소 추가
     *
     * @param request 충전소 추가 요청
     * @return 충전소 아이디
     */
    @Transactional
    public String addStation(AddStationRequest request) {
        Station station = getStationOrNull(request.id());

        throwIfStationExists(station);
        throwIfCompanyNotExists(request.companyId());

        return addOrRestoreStation(request, station).getId();
    }

    /**
     * 충전소 목록 조회
     *
     * @param pageable 페이징 정보
     * @param request
     * @return 충전소 목록
     */
    @Transactional(readOnly = true)
    public Page<StationResponse> getStationList(Pageable pageable, SearchStationRequest request) {
        return stationRepository.findAll(pageable, request).map(StationResponse::fromEntity);
    }

    /**
     * 충전소 수정
     *
     * @param id      충전소 아이디
     * @param request 충전소 수정 요청
     */
    @Transactional
    public void updateStation(String id, UpdateStationRequest request) {
        Station station = getStationOrThrow(id);

        throwIfStationDeleted(station);

        station.update(request.name(), request.address(), request.useTime(), request.areaCode(),
            request.areaDetailCode(), request.stationKindCode(), request.stationKindDetailCode(),
            request.parkingFreeYn(), request.note(), request.useLimitYn(), request.useLimitDetail(),
            request.trafficYn(), request.latitude(), request.longitude());
    }

    /**
     * 충전소 삭제
     *
     * @param id 충전소 아이디
     */
    @Transactional
    public void deleteStation(String id) {
        Station station = getStationOrThrow(id);

        throwIfStationDeleted(station);

        station.delete();
    }

    /**
     * 충전소 요약 목록 조회
     *
     * @param pageable  페이징 정보
     * @param condition 검색 조건
     * @return 충전소 요약 목록
     */
    @Transactional(readOnly = true)
    public Slice<StationSummary> searchStationSummaryList(Pageable pageable,
        SearchStationSummaryCondition condition) {
        return stationRepository.findAllStationSummary(pageable, condition);
    }

    private void throwIfStationExists(Station station) {
        if (station != null && !station.isDeleted()) {
            throw new CustomException(STATION_ALREADY_EXISTS);
        }
    }

    private void throwIfStationDeleted(Station station) {
        if (station.isDeleted()) {
            throw new CustomException(STATION_ALREADY_DELETED);
        }
    }

    private void throwIfCompanyNotExists(String companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new CustomException(COMPANY_NOT_FOUND);
        }
    }

    private Station getStationOrThrow(String id) {
        return Optional.ofNullable(getStationOrNull(id))
            .orElseThrow(() -> new CustomException(STATION_NOT_FOUND));
    }

    private Station getStationOrNull(String id) {
        return stationRepository.findById(id).orElse(null);
    }

    private Station addOrRestoreStation(AddStationRequest request, Station station) {
        if (station == null) {
            station = request.toEntity();
            stationRepository.save(station);
        } else {
            station.restore();
        }

        return station;
    }
}
