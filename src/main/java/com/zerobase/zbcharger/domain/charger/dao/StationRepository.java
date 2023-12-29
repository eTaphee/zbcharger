package com.zerobase.zbcharger.domain.charger.dao;

import com.zerobase.zbcharger.domain.charger.entity.Station;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, String> {

    @Query("select id from Station")
    Set<String> findAllIds();
}
