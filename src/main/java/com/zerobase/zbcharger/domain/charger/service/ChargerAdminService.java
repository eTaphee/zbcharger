package com.zerobase.zbcharger.domain.charger.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.CHARGER_ALREADY_DELETED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.CHARGER_ALREADY_EXISTS;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.CHARGER_NOT_FOUND;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.STATION_NOT_FOUND;

import com.zerobase.zbcharger.domain.charger.dao.ChargerRepository;
import com.zerobase.zbcharger.domain.charger.dao.StationRepository;
import com.zerobase.zbcharger.domain.charger.dto.AddChargerRequest;
import com.zerobase.zbcharger.domain.charger.dto.ChargerInfo;
import com.zerobase.zbcharger.domain.charger.dto.UpdateChargerRequest;
import com.zerobase.zbcharger.domain.charger.entity.Charger;
import com.zerobase.zbcharger.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChargerAdminService {

    private final ChargerRepository chargerRepository;
    private final StationRepository stationRepository;

    @Transactional
    public String addCharger(AddChargerRequest request) {
        Charger charger = chargerRepository.findById(request.id()).orElse(null);

        throwIfChargerExists(charger);
        throwIfStationNotExists(request.stationId());

        if (charger == null) {
            charger = request.toEntity();
            chargerRepository.save(charger);
        } else {
            charger.restore();
        }

        return charger.getId();
    }

    @Transactional(readOnly = true)
    public Page<ChargerInfo> getChargerList(Pageable pageable) {
        return chargerRepository.findAll(pageable).map(ChargerInfo::fromEntity);
    }

    @Transactional
    public void updateCharger(String id, UpdateChargerRequest request) {
        Charger charger = getChargerOrThrow(id);

        throwIfChargerDeleted(charger);

        charger.update(request.chargerType(), request.location(), request.output(),
            request.method());
    }

    @Transactional
    public void deleteCharger(String id) {
        Charger charger = getChargerOrThrow(id);

        throwIfChargerDeleted(charger);

        charger.delete();
    }

    private void throwIfChargerExists(Charger charger) {
        if (charger != null && !charger.isDeletedYn()) {
            throw new CustomException(CHARGER_ALREADY_EXISTS);
        }
    }


    private void throwIfChargerDeleted(Charger charger) {
        if (charger.isDeletedYn()) {
            throw new CustomException(CHARGER_ALREADY_DELETED);
        }
    }

    private void throwIfStationNotExists(String stationId) {
        if (!stationRepository.existsById(stationId)) {
            throw new CustomException(STATION_NOT_FOUND);
        }
    }

    private Charger getChargerOrThrow(String id) {
        return chargerRepository.findById(id)
            .orElseThrow(() -> new CustomException(CHARGER_NOT_FOUND));
    }
}
