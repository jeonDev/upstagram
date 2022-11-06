package com.api.upstagram.domain.Ad;

import com.api.upstagram.vo.Ad.AdRVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {

    @Query(value =
            "SELECT a, ac" +
            "  FROM Ad a" +
            "  JOIN AdCompany ac ON a.adCompany = ac.adCompanyNo" +
            " ORDER BY a.regDttm DESC"
    )
    List<Ad> selectAdList();
}
