package com.api.upstagram.domain.Ad.Repository;

import com.api.upstagram.domain.Ad.Entity.AdCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdCompanyRepository extends JpaRepository<AdCompany, Long> {

    // 광고회사 조회
    @Query(value =
            "SELECT ac" +
            "  fROM AdCompany ac" +
            " ORDER BY ac.regDttm DESC"
    )
    List<AdCompany> selectAdCompanyList();
}
