package com.zerobase.zbcharger.domain.charger.controller.admin;

import com.zerobase.zbcharger.configuration.security.annotation.RoleAdmin;
import com.zerobase.zbcharger.domain.charger.dto.admin.AddChargerRequest;
import com.zerobase.zbcharger.domain.charger.dto.admin.ChargerResponse;
import com.zerobase.zbcharger.domain.charger.dto.admin.UpdateChargerRequest;
import com.zerobase.zbcharger.domain.charger.service.admin.ChargerService;
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
public class ChargerController {

    private final ChargerService chargerService;

    @PostMapping
    public ResponseEntity<Void> addCharger(@Valid @RequestBody AddChargerRequest request) {
        return ResponseEntityUtils.created(chargerService.addCharger(request));
    }

    @GetMapping
    public ResponseEntity<Page<ChargerResponse>> getChargerList(
        @PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(chargerService.getChargerList(pageable));
    }

    @DeleteMapping("{id}")
    public void deleteCharger(@PathVariable String id) {
        chargerService.deleteCharger(id);
    }

    @PatchMapping("{id}")
    public void updateCharger(@PathVariable String id,
        @Valid @RequestBody UpdateChargerRequest request) {
        chargerService.updateCharger(id, request);
    }
}
