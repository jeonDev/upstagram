package com.api.upstagram.domain.MemberInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, String> {
    Optional<MemberInfo> findByIdAndUseYn(String id, String useYn);
    Optional<MemberInfo> findByNickname(String nickname);

    @Query(value =
            "SELECT m" +
            "  FROM MemberInfo m" +
            " ORDER BY m.joinDttm DESC"
    )
    List<MemberInfo> selectMemberInfoList();
}
