package com.api.upstagram.domain.MemberInfo.Repository;

import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.domain.FollowUser.Entity.QFollowUser;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.domain.MemberInfo.Entity.QMemberInfo;
import com.api.upstagram.vo.MemberInfo.MemberInfoRVO;
import com.api.upstagram.vo.MemberInfo.QMemberInfoRVO;
import com.api.upstagram.vo.Search.SearchPVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class MemberInfoDslRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberInfoDslRepository(JPAQueryFactory jpaQueryFactory) {
        super(MemberInfo.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<MemberInfoRVO> selectSearchMemberInfoList(SearchPVO pvo) {
        QMemberInfo memberInfo = QMemberInfo.memberInfo;
        QFollowUser followUser = QFollowUser.followUser;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(memberInfo.id.ne(pvo.getId()));
        booleanBuilder.and(memberInfo.name.contains(pvo.getSearchValue())
                .or(memberInfo.nickname.contains(pvo.getSearchValue())));

        if(!StringUtils.isNotEmpty(pvo.getSearchDivisionCode()) && "3".equals(pvo.getSearchDivisionCode()))
            booleanBuilder.and(memberInfo.tagAllowYn.eq("Y"));

        return jpaQueryFactory
                .select(new QMemberInfoRVO(
                        memberInfo.id,
                        memberInfo.name,
                        memberInfo.nickname,
                        memberInfo.sex,
                        memberInfo.tel,
                        memberInfo.oauthNo,
                        memberInfo.profileImage,
                        followUser.followNo.coalesce(0L).longValue()
                ))
                .from(memberInfo)
                .leftJoin(memberInfo.followUser, followUser)
                    .on(followUser.idMember.id.eq(pvo.getId()))
                .where(booleanBuilder)
                .orderBy(followUser.followNo.asc().nullsLast(), memberInfo.name.desc())
                .fetch();
    }
}
