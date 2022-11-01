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
    @Query(nativeQuery = true,
        value = "SELECT f.FOLLOW_NO AS followNo" +
                "     , f.ID AS id" +
                "     , f.FOLLOW_ID AS followId" +
                "     , m.NAME AS name" +
                "     , m.NICKNAME AS nickname" +
                "     , m.SEX AS sex" +
                "     , m.TEL AS tel" +
                "     , m1.NAME AS followName" +
                "     , m1.NICKNAME AS followNickname" +
                "     , m1.SEX AS followSex" +
                "     , m1.TEL AS followTel" +
                " FROM FOLLOW_USER f" +
                " JOIN MEMBER_INFO m ON f.ID = m.ID " +
//                "  AND m.USE_YN = 'Y'" +
                " JOIN MEMBER_INFO m1 ON f.FOLLOW_ID = m1.ID" +
                "  AND m1.USE_YN = 'Y'" +
                "WHERE f.ID = :id")
    public List<FollowUserInterface> findByFollowUserList(@Param("id") String id);
    
    /* Follower 조회 */
    @Query(nativeQuery = true,
        value = "SELECT f.FOLLOW_NO AS followNo" +
                "     , f.ID AS id" +
                "     , f.FOLLOW_ID AS followId" +
                "     , m.NAME AS name" +
                "     , m.NICKNAME AS nickname" +
                "     , m.SEX AS sex" +
                "     , m.TEL AS tel" +
                "     , m1.NAME AS followName" +
                "     , m1.NICKNAME AS followNickname" +
                "     , m1.SEX AS followSex" +
                "     , m1.TEL AS followTel" +
                " FROM FOLLOW_USER f" +
                " JOIN MEMBER_INFO m ON f.FOLLOW_ID = m.ID " +
//                "  AND m.USE_YN = 'Y'" +
                " JOIN MEMBER_INFO m1 ON f.ID = m1.ID" +
                "  AND m1.USE_YN = 'Y'" +
                "WHERE f.FOLLOW_ID = :followId")
    public List<FollowUserInterface> findByFollowerUserList(@Param("followId") String followId);

    List<FollowUser> findByFollowMember(MemberInfo followMember);
    Optional<FollowUser> findByFollowNo(Long followNo);
}
