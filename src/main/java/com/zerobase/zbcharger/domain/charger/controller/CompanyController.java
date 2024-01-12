package com.zerobase.zbcharger.domain.charger.controller;

import com.zerobase.zbcharger.configuration.security.annotation.RoleAdmin;
import com.zerobase.zbcharger.domain.charger.dto.AddCompanyRequest;
import com.zerobase.zbcharger.domain.charger.dto.SearchCompanyCondition;
import com.zerobase.zbcharger.domain.charger.dto.CompanyResponse;
import com.zerobase.zbcharger.domain.charger.dto.UpdateCompanyRequest;
import com.zerobase.zbcharger.domain.charger.service.CompanyService;
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
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<Void> addCompany(@Valid @RequestBody AddCompanyRequest request) {
        return ResponseEntityUtils.created(companyService.addCompany(request));
    }

    @GetMapping
    public Page<CompanyResponse> searchCompanyList(
        @PageableDefault(sort = "id", direction = Direction.ASC) Pageable pageable,
        SearchCompanyCondition condition) {
        return companyService.searchCompanyList(pageable, condition);
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
