package com.zerobase.zbcharger.domain.charger.service;

import com.zerobase.zbcharger.api.DataGoKrApi;
import com.zerobase.zbcharger.domain.charger.dao.ChargerRepository;
import com.zerobase.zbcharger.domain.charger.dao.CompanyRepository;
import com.zerobase.zbcharger.domain.charger.dao.StationRepository;
import com.zerobase.zbcharger.domain.charger.dto.ChargerInfo;
import com.zerobase.zbcharger.domain.charger.dto.ChargerInfoResponse;
import com.zerobase.zbcharger.domain.charger.entity.Charger;
import com.zerobase.zbcharger.domain.charger.entity.Company;
import com.zerobase.zbcharger.domain.charger.entity.Station;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 충전소 정보 공공 API 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StationOpenApiService {

    private static final int MAX_NUM_OF_ROWS = 9999;

    @Value("${data-go-kr.serviceKey}")
    private String serviceKey;

    private final DataGoKrApi dataGoKrApi;

    private final CompanyRepository companyRepository;
    private final StationRepository stationRepository;
    private final ChargerRepository chargerRepository;

    @Transactional
    @Scheduled(cron = "0/5 * * * * *")
    public void syncChargerInformationFromDataGoKrApi() {
        long start = System.currentTimeMillis();
        log.info("syncChargerInformationFromDataGoKrApi schedule started");

        List<ChargerInfo> chargerInfos = getChargerInfos();

        upsertCompany(chargerInfos);
        upsertStation(chargerInfos);
        upsertCharger(chargerInfos);

        long end = System.currentTimeMillis();
        log.info("syncChargerInformationFromDataGoKrApi schedule finished - {} ms", end - start);
    }

    /**
     * 모든 충전소 정보 조회
     *
     * @return 충전소 목록
     */
    private List<ChargerInfo> getChargerInfos() {
        List<ChargerInfo> chargerInfos = new ArrayList<>();
        int pageNo = 1;
        int totalCount = -1;

        do {
            try {
                ChargerInfoResponse response = dataGoKrApi
                    .getChargerInformation(serviceKey, pageNo++, MAX_NUM_OF_ROWS);

                totalCount = (totalCount == -1) ? response.getTotalCount() : totalCount;

                if (response.getItems().isEmpty()) {
                    break;
                }

                chargerInfos.addAll(response.getItems());

                log.info("get chargerInfo page={}, count={}",
                    response.getPageNo(),
                    response.getItems().size());
            } catch (Exception e) {
                log.error("exception is occurred. {}, pageNo={}", e, pageNo - 1);
            }
        } while (totalCount <= chargerInfos.size());

        log.info("total chargerInfo={}", chargerInfos.size());

        return chargerInfos;
    }

    private void upsertCompany(List<ChargerInfo> chargerInfos) {
        Set<String> allIds = companyRepository.findAllIds();

        List<Company> companyList = chargerInfos.stream()
            .collect(Collectors.groupingBy(ChargerInfo::getCompany))
            .keySet()
            .stream()
            .map(m -> {
                Company company = m.toEntity();
                boolean isNew = !allIds.contains(m.getId());
                company.setIsNewForPersistable(isNew);
                return company;
            })
            .toList();

        companyRepository.saveAll(companyList);

        log.info("company count={}", companyList.size());
    }

    private void upsertStation(List<ChargerInfo> chargerInfos) {
        Set<String> allIds = stationRepository.findAllIds();

        List<Station> stationList = chargerInfos.stream()
            .collect(Collectors.groupingBy(ChargerInfo::getStation))
            .keySet()
            .stream()
            .map(m -> {
                Station station = m.toEntity();
                boolean isNew = !allIds.contains(m.getId());
                station.setIsNewForPersistable(isNew);
                return station;
            })
            .toList();

        stationRepository.saveAll(stationList);

        log.info("station count={}", stationList.size());
    }

    private void upsertCharger(List<ChargerInfo> chargerInfos) {
        Set<String> allIds = chargerRepository.findAllIds();

        List<Charger> chargerList = chargerInfos.stream()
            .map(m -> {
                Charger charger = m.toEntity();
                String chargerId = m.getStationId() + m.getChargerId();
                boolean isNew = !allIds.contains(chargerId);
                charger.setIsNewForPersistable(isNew);
                return charger;
            })
            .toList();

        chargerRepository.saveAll(chargerList);

        log.info("charger count={}", chargerList.size());
    }
}
