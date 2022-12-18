package com.api.upstagram.domain.MemberInfo.Repository;

import java.util.List;
import java.util.Optional;

import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberInfoRepository extends JpaRepository<MemberInfo, String> {
    Optional<MemberInfo> findByIdAndUseYn(String id, String useYn);
    Optional<MemberInfo> findByNickname(String nickname);

    @Query(value =
            "SELECT m" +
            "  FROM MemberInfo m" +
            " ORDER BY m.joinDttm DESC"
    )
    List<MemberInfo> selectMemberInfoList();

    @Query(value =
            "SELECT m" +
            "  FROM MemberInfo m" +
            "  LEFT JOIN FollowUser fu ON fu.followMember.id = m.id" +
            "   AND fu.idMember.id = :id" +
            " WHERE 1=1" +
            "   AND (" +
            "       m.name LIKE '%' || :searchValue || '%'" +
            "    OR m.nickname LIKE '%' || :searchValue || '%'" +
            "       )" +
            " ORDER BY fu.idMember.id NULLS LAST")
    List<MemberInfo> selectSearchMemberInfoList(@Param("id") String id,
                                                @Param("searchValue") String searchValue);
}
