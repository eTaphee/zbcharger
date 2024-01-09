package com.zerobase.zbcharger.domain.charger.service.admin;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.CHARGER_ALREADY_DELETED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.CHARGER_ALREADY_EXISTS;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.CHARGER_NOT_FOUND;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.STATION_NOT_FOUND;

import com.zerobase.zbcharger.domain.charger.dao.ChargerRepository;
import com.zerobase.zbcharger.domain.charger.dao.StationRepository;
import com.zerobase.zbcharger.domain.charger.dto.admin.AddChargerRequest;
import com.zerobase.zbcharger.domain.charger.dto.ChargerInfo;
import com.zerobase.zbcharger.domain.charger.dto.admin.ChargerResponse;
import com.zerobase.zbcharger.domain.charger.dto.admin.UpdateChargerRequest;
import com.zerobase.zbcharger.domain.charger.entity.Charger;
import com.zerobase.zbcharger.exception.CustomException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChargerService {

    private final ChargerRepository chargerRepository;
    private final StationRepository stationRepository;

    @Transactional
    public String addCharger(AddChargerRequest request) {
        Charger charger = getChargerOrNull(request.id());

        throwIfChargerExists(charger);
        throwIfStationNotExists(request.stationId());

        return addOrRestoreCharger(request, charger).getId();
    }

    @Transactional(readOnly = true)
    public Page<ChargerResponse> getChargerList(Pageable pageable) {
        return chargerRepository.findAll(pageable).map(ChargerResponse::fromEntity);
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
        return Optional.ofNullable(getChargerOrNull(id))
            .orElseThrow(() -> new CustomException(CHARGER_NOT_FOUND));
    }

    private Charger getChargerOrNull(String id) {
        return chargerRepository.findById(id).orElse(null);
    }

    private Charger addOrRestoreCharger(AddChargerRequest request, Charger charger) {
        if (charger == null) {
            charger = request.toEntity();
            chargerRepository.save(charger);
        } else {
            charger.restore();
        }
        return charger;
    }
}
