package com.api.upstagram.domain.Ad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdManageRepository extends JpaRepository<AdManage, Long> {
    Optional<AdManage> findByAd(Ad ad);

    /* 광고별 노출시간 조회 */
    @Query(value =
            "SELECT am, a" +
            "  FROM AdManage am" +
            "  JOIN Ad a ON am.ad = a.adNo" +
            " ORDER BY am.startDttm DESC, am.endDttm DESC, am.startTime DESC, am.endTime DESC")
    List<AdManage> selectAdManageList();
}
