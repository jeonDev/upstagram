package com.api.upstagram.domain.Ad.Repository;

import com.api.upstagram.domain.Ad.Entity.AdViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdViewHistoryRepository extends JpaRepository<AdViewHistory, Long> {

    @Query(value =
            "SELECT a, m" +
            "  FROM AdViewHistory a" +
            "  JOIN MemberInfo m ON a.member = m.id" +
            " WHERE a.ad.adNo = :adNo" +
            " ORDER BY a.viewDttm DESC"
    )
    List<AdViewHistory> selectAdViewHistoryList(@Param("adNo") Long adNo);
}
