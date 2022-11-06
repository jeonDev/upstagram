package com.api.upstagram.domain.Ad;

import com.api.upstagram.vo.Ad.AdRVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {

    @Query(value =
            "SELECT a, ac" +
            "  FROM Ad a" +
            "  JOIN AdCompany ac ON a.adCompany = ac.adCompanyNo" +
            "  JOIN AdManage am ON a.adNo = am.ad" +
            " WHERE 1=1" +
            "   AND :dttm BETWEEN am.startDttm AND am.endDttm" +
            "   AND :time BETWEEN am.startTime AND am.endTime" +
            " ORDER BY a.regDttm DESC")
    List<Ad> selectAdList(@Param("dttm") String dttm, @Param("time") String time);

    @Query(value =
            "SELECT a, ac" +
                    "  FROM Ad a" +
                    "  JOIN AdCompany ac ON a.adCompany = ac.adCompanyNo" +
                    " ORDER BY a.regDttm DESC")
    List<Ad> selectAllAdList();
}
