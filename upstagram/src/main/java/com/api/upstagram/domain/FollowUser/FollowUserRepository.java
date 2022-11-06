package com.api.upstagram.domain.FollowUser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.FollowUser.FollowUserInterface;

public interface FollowUserRepository extends JpaRepository<FollowUser, Long> {

    /* Follow 조회 */
    @Query(value =
            "SELECT f" +
            "     , me" +
            "     , follow" +
            "  FROM FollowUser f" +
            "  JOIN MemberInfo me ON f.idMember = me.id" +
            "   AND f.idMember.id = :id" +
            "  JOIN MemberInfo follow ON f.followMember = follow.id" +
            "   AND follow.useYn = 'Y'")
    List<FollowUser> selectFollowUserList(@Param("id") String id);

    /* Follower 조회*/
    @Query(value =
            "SELECT f" +
            "     , follower" +
            "     , follow" +
            "  FROM FollowUser f" +
            "  JOIN MemberInfo follower ON f.idMember = follower.id" +
            "   AND follower.useYn = 'Y'" +
            "  JOIN MemberInfo follow ON f.followMember = follow.id" +
            "   AND f.followMember.id = :id")
    List<FollowUser> selectFollowerUserList(@Param("id") String id);

    Optional<FollowUser> findByFollowNo(Long followNo);
}
