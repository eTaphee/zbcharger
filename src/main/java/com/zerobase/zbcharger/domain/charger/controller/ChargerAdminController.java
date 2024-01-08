package com.zerobase.zbcharger.domain.charger.controller;

import com.zerobase.zbcharger.configuration.security.annotation.RoleAdmin;
import com.zerobase.zbcharger.domain.charger.dto.AddChargerRequest;
import com.zerobase.zbcharger.domain.charger.dto.ChargerInfo;
import com.zerobase.zbcharger.domain.charger.dto.UpdateChargerRequest;
import com.zerobase.zbcharger.domain.charger.service.ChargerAdminService;
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
@RequestMapping("/admin/chargers")
@RequiredArgsConstructor
public class ChargerAdminController {

    private final ChargerAdminService chargerAdminService;

    @PostMapping
    public ResponseEntity<Void> addCharger(@Valid @RequestBody AddChargerRequest request) {
        return ResponseEntityUtils.created(chargerAdminService.addCharger(request));
    }

    @GetMapping
    public ResponseEntity<Page<ChargerInfo>> getChargerList(
        @PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(chargerAdminService.getChargerList(pageable));
    }

    @DeleteMapping("{id}")
    public void deleteCharger(@PathVariable String id) {
        chargerAdminService.deleteCharger(id);
    }

    @PatchMapping("{id}")
    public void updateCharger(@PathVariable String id,
        @Valid @RequestBody UpdateChargerRequest request) {
        chargerAdminService.updateCharger(id, request);
    }
}
