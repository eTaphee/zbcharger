package com.zerobase.zbcharger.domain.charger.controller.admin;

import com.zerobase.zbcharger.configuration.security.annotation.RoleAdmin;
import com.zerobase.zbcharger.domain.charger.dto.admin.AddStationRequest;
import com.zerobase.zbcharger.domain.charger.dto.admin.SearchStationRequest;
import com.zerobase.zbcharger.domain.charger.dto.admin.StationResponse;
import com.zerobase.zbcharger.domain.charger.dto.admin.UpdateStationRequest;
import com.zerobase.zbcharger.domain.charger.service.admin.StationService;
import com.zerobase.zbcharger.util.ResponseEntityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RoleAdmin
@RestController
@RequestMapping("/admin/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PostMapping
    public ResponseEntity<Void> addStation(@Valid @RequestBody AddStationRequest request) {
        return ResponseEntityUtils.created(stationService.addStation(request));
    }

    @GetMapping
    public ResponseEntity<Page<StationResponse>> getStationList(
        @PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageable,
        SearchStationRequest request) {
        return ResponseEntity.ok(stationService.getStationList(pageable, request));
    }

    @DeleteMapping("{id}")
    public void deleteStation(@PathVariable String id) {
        stationService.deleteStation(id);
    }

    @PatchMapping("{id}")
    public void updateStation(@PathVariable String id,
        @Valid @RequestBody UpdateStationRequest request) {
        stationService.updateStation(id, request);
    }
}
