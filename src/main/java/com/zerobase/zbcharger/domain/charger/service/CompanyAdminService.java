package com.zerobase.zbcharger.domain.charger.service;

import static com.zerobase.zbcharger.exception.constant.ErrorCode.COMPANY_ALREADY_DELETED;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.COMPANY_ALREADY_EXISTS;
import static com.zerobase.zbcharger.exception.constant.ErrorCode.COMPANY_NOT_FOUND;

import com.zerobase.zbcharger.domain.charger.dao.CompanyRepository;
import com.zerobase.zbcharger.domain.charger.dto.AddCompanyRequest;
import com.zerobase.zbcharger.domain.charger.dto.CompanyInfo;
import com.zerobase.zbcharger.domain.charger.dto.UpdateCompanyRequest;
import com.zerobase.zbcharger.domain.charger.entity.Company;
import com.zerobase.zbcharger.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자용 회사 서비스
 */
@Service
@RequiredArgsConstructor
public class CompanyAdminService {

    private final CompanyRepository companyRepository;

    /**
     * 회사 추가
     *
     * @param request 회사 추가 요청
     * @return 회사 아이디
     */
    @Transactional
    public String addCompany(AddCompanyRequest request) {
        Company company = companyRepository.findById(request.id()).orElse(null);

        throwIfCompanyExists(company);

        if (company == null) {
            company = request.toEntity();
            companyRepository.save(company);
        } else {
            company.restore();
        }

        return company.getId();
    }

    /**
     * 회사 수정
     *
     * @param id      회사 아이디
     * @param request 회사 수정 요청
     */
    @Transactional
    public void updateCompany(String id, UpdateCompanyRequest request) {
        Company company = getCompanyOrThrow(id);

        throwIfCompanyDeleted(company);

        company.update(request.name(), request.tel(), request.operator());
    }

    /**
     * 회사 삭제
     *
     * @param id 회사 아이디
     */
    @Transactional
    public void deleteCompany(String id) {
        Company company = getCompanyOrThrow(id);

        throwIfCompanyDeleted(company);

        company.delete();

        // TODO: station, charger delete 여부
    }

    /**
     * 회사 목록 조회
     *
     * @param pageable 페이징 정보
     * @return 회사 목록
     */
    @Transactional(readOnly = true)
    public Page<CompanyInfo> getCompanyList(Pageable pageable) {
        return companyRepository.findAll(pageable).map(CompanyInfo::fromEntity);
    }

    private void throwIfCompanyExists(Company company) {
        if (company != null && !company.isDeleted()) {
            throw new CustomException(COMPANY_ALREADY_EXISTS);
        }
    }

    private void throwIfCompanyDeleted(Company company) {
        if (company.isDeleted()) {
            throw new CustomException(COMPANY_ALREADY_DELETED);
        }
    }

    private Company getCompanyOrThrow(String id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new CustomException(COMPANY_NOT_FOUND));
    }
}
