package com.zerobase.zbcharger.domain.charger.dao;

import com.zerobase.zbcharger.domain.charger.entity.Charger;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerRepository extends JpaRepository<Charger, String> {

    @Query("select id from Charger")
    Set<String> findAllIds();
}
