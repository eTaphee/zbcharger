package com.zerobase.zbcharger.domain.charger.dao;

import com.zerobase.zbcharger.domain.charger.dao.custom.CustomCompanyRepository;
import com.zerobase.zbcharger.domain.charger.entity.Company;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String>, CustomCompanyRepository {

    @Query("select id from Company")
    Set<String> findAllIds();
}
