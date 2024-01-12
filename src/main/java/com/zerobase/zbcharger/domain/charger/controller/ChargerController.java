package com.zerobase.zbcharger.domain.charger.controller;

import com.zerobase.zbcharger.configuration.security.annotation.RoleAdmin;
import com.zerobase.zbcharger.domain.charger.dto.ChargerSummary;
import com.zerobase.zbcharger.domain.charger.dto.SearchChargerSummaryCondition;
import com.zerobase.zbcharger.domain.charger.dto.AddChargerRequest;
import com.zerobase.zbcharger.domain.charger.dto.ChargerResponse;
import com.zerobase.zbcharger.domain.charger.dto.UpdateChargerRequest;
import com.zerobase.zbcharger.domain.charger.service.admin.ChargerService;
import com.zerobase.zbcharger.util.ResponseEntityUtils;
import jakarta.validation.Valid;
import java.util.List;
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

@RestController
@RequestMapping("/chargers")
@RequiredArgsConstructor
public class ChargerController {

    private final ChargerService chargerService;

    @RoleAdmin
    @PostMapping
    public ResponseEntity<Void> addCharger(@Valid @RequestBody AddChargerRequest request) {
        return ResponseEntityUtils.created(chargerService.addCharger(request));
    }

    @RoleAdmin
    @GetMapping
    public Page<ChargerResponse> searchChargerList(
        @PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageable) {
        return chargerService.searchChargerList(pageable);
    }

    @RoleAdmin
    @DeleteMapping("{id}")
    public void deleteCharger(@PathVariable String id) {
        chargerService.deleteCharger(id);
    }

    @RoleAdmin
    @PatchMapping("{id}")
    public void updateCharger(@PathVariable String id,
        @Valid @RequestBody UpdateChargerRequest request) {
        chargerService.updateCharger(id, request);
    }

    @GetMapping("/summary")
    public List<ChargerSummary> searchChargerSummaryList(SearchChargerSummaryCondition condition) {
        return chargerService.searchChargerSummaryList(condition);
    }
}
