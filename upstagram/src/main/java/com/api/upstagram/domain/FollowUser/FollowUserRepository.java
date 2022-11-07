package com.api.upstagram.domain.FollowUser;

import java.util.List;
import java.util.Optional;

import com.api.upstagram.vo.FollowUser.RecommandInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /* Follow 추천 조회 */
    @Query(value =
            "SELECT MAX(f.followNo) AS followNo" +
            "     , MAX(m.id) AS followId" +
            "     , MAX(m.name) AS followName" +
            "     , MAX(m.nickname) AS followNickname" +
            "     , MAX(m.sex) AS followSex" +
            "     , MAX(m.tel) AS followTel" +
            "     , MAX(m.oauthNo) AS followOauthNo" +
            "     , COUNT(f.idMember) AS friendCnt" +
            "  FROM FollowUser f" +
            "  JOIN FollowUser fu ON fu.followMember = f.idMember" +
            "   AND fu.idMember.id = :id" +
            "  JOIN MemberInfo m ON f.followMember = m.id" +
            "   AND m.useYn = 'Y'" +
            " WHERE f.followMember.id <> :id" +
            " GROUP BY f.followMember.id" +
            " ORDER BY COUNT(f.followMember.id) DESC")
    List<RecommandInterface> selectFollowRecommanderList(@Param("id") String id);

    @Query(value =
            "SELECT MAX(f.followNo) AS followNo" +
            "     , MAX(m.id) AS followId" +
            "     , MAX(m.name) AS followName" +
            "     , MAX(m.nickname) AS followNickname" +
            "     , MAX(m.sex) AS followSex" +
            "     , MAX(m.tel) AS followTel" +
            "     , MAX(m.oauthNo) AS followOauthNo" +
            "     , COUNT(f.idMember) AS friendCnt" +
            "  FROM FollowUser f" +
            "  JOIN MemberInfo m ON f.followMember = m.id" +
            "   AND m.useYn = 'Y'" +
            " WHERE f.followMember.id <> :id" +
            " GROUP BY f.followMember.id" +
            " ORDER BY COUNT(f.followMember.id) DESC")
    List<RecommandInterface> selectFollowTopRecommanderList(@Param("id")String id);
}
