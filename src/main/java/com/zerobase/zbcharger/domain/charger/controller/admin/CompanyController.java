package com.zerobase.zbcharger.domain.charger.controller.admin;

import com.zerobase.zbcharger.configuration.security.annotation.RoleAdmin;
import com.zerobase.zbcharger.domain.charger.dto.admin.AddCompanyRequest;
import com.zerobase.zbcharger.domain.charger.dto.admin.CompanyResponse;
import com.zerobase.zbcharger.domain.charger.dto.admin.UpdateCompanyRequest;
import com.zerobase.zbcharger.domain.charger.service.admin.CompanyService;
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
@RequestMapping("/admin/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Void> addCompany(@Valid @RequestBody AddCompanyRequest request) {
        return ResponseEntityUtils.created(companyService.addCompany(request));
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponse>> getCompanyList(
        @PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(companyService.getCompanyList(pageable));
    }

    @DeleteMapping("{id}")
    public void deleteCompany(@PathVariable String id) {
        companyService.deleteCompany(id);
    }

    @PatchMapping("{id}")
    public void updateCompany(@PathVariable String id,
        @Valid @RequestBody UpdateCompanyRequest request) {
        companyService.updateCompany(id, request);
    }
}
