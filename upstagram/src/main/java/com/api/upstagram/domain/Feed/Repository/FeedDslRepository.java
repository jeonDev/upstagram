package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.domain.Feed.Entity.*;
import com.api.upstagram.domain.FollowUser.Entity.QFollowUser;
import com.api.upstagram.domain.MemberInfo.Entity.QMemberInfo;
import com.api.upstagram.vo.Feed.*;
import com.api.upstagram.vo.Search.SearchPVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class FeedDslRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public FeedDslRepository(JPAQueryFactory jpaQueryFactory) {
        super(Feed.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * Feed List 조회
     * */
    public List<FeedRVO> selectFollowFeedList(FeedListPVO pvo) {

        QFeed feed = QFeed.feed;
        QFollowUser followUser = QFollowUser.followUser;
        QMemberInfo memberInfo = QMemberInfo.memberInfo;
        QFeedFile feedFile = QFeedFile.feedFile;
        QFeedHeart feedHeart = QFeedHeart.feedHeart;
        QFeedComment feedComment = QFeedComment.feedComment;
        QFeedKeep feedKeep = QFeedKeep.feedKeep;
        QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
        QFeedTag feedTag = QFeedTag.feedTag;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // Feed 사용여부 체크
        booleanBuilder.and(feed.useYn.eq("Y"));

        // Feed 작성자 체크
        if(!StringUtils.isNotEmpty(pvo.getWriterId()))
            booleanBuilder.and(feed.member.id.eq(pvo.getWriterId()));

        // Feed Tag Id 포함 체크
        if(!StringUtils.isNotEmpty(pvo.getTagId()))
            booleanBuilder.and(feedTag.member.id.in(pvo.getWriterId()));

        if(!StringUtils.isNotEmpty(pvo.getHashtag()))
            booleanBuilder.and(feedHashtag.hashtag.in(pvo.getHashtag()));

        return jpaQueryFactory
                .select(new QFeedRVO(
                        feed.feedNo.longValue(),
                        feed.title.max(),
                        Expressions.stringTemplate("GROUP_CONCAT({0}, '#')", feedHashtag.hashtag),
                        feed.useYn.max(),
                        feed.member.id.max(),
                        feed.member.name.max(),
                        feed.member.nickname.max(),
                        feed.member.sex.max(),
                        feed.member.tel.max(),
                        feed.member.oauthNo.max(),
                        feedHeart.feedHeartNo.countDistinct().intValue(),
                        feedComment.feedCommentNo.countDistinct().intValue(),
                        Expressions.stringTemplate("GROUP_CONCAT({0})", feedFile.fileName),
                        Expressions.stringTemplate("GROUP_CONCAT({0})", feedFile.fileExt),
                        feedKeep.feedKeepNo.max(),
                        new CaseBuilder()
                                .when(feedHeart.member.id.eq(pvo.getId())).then(feedHeart.feedHeartNo)
                                .otherwise(Expressions.nullExpression()).max())               // Feed Heart 좋아요 유무 체크
                )
                .from(feed)
                .join(feed.member, memberInfo)
                    .on(memberInfo.useYn.eq("Y"))                  // 작성자 유저 정보 (사용여부 Y인 유저만)
                .join(memberInfo.followUser, followUser)
                    .on(followUser.idMember.id.eq(pvo.getId()))         // 내가 Follow 한 멤버의 Feed
                .join(feed.feedFile, feedFile)                          // Upload 파일
                .leftJoin(feed.feedHeart, feedHeart)                    // Feed 좋아요
                .leftJoin(feed.feedComment, feedComment)                // Feed 댓글 수
                .leftJoin(feed.feedKeep, feedKeep)
                    .on(feedKeep.member.id.eq(pvo.getId()))             // Feed Keep 여부 체크
                .leftJoin(feed.feedHashtags, feedHashtag)               // Feed Hashtag
                .where(booleanBuilder)
                .groupBy(feed.feedNo)
                .fetch();
    }

    /**
     * Feed List 조회
     * */
    public List<FeedRVO> selectFeedList(FeedListPVO pvo) {

        QFeed feed = QFeed.feed;
        QMemberInfo memberInfo = QMemberInfo.memberInfo;
        QFeedFile feedFile = QFeedFile.feedFile;
        QFeedHeart feedHeart = QFeedHeart.feedHeart;
        QFeedComment feedComment = QFeedComment.feedComment;
        QFeedKeep feedKeep = QFeedKeep.feedKeep;
        QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // Feed 사용여부 체크
        booleanBuilder.and(feed.useYn.eq("Y"));

        // Feed Keep 여부 체크
        if("Y".equals(pvo.getFeedKeepYn()))
            booleanBuilder.and(feedKeep.member.id.eq(pvo.getId()));

        // User Feed
        if("3".equals(pvo.getFeedDivisionCode()) && !StringUtils.isNotEmpty(pvo.getWriterId()))
            booleanBuilder.and(feed.member.id.eq(pvo.getWriterId()));

        if(!StringUtils.isNotEmpty(pvo.getHashtag()))
            booleanBuilder.and(feedHashtag.hashtag.in(pvo.getHashtag()));

        if("Y".equals(pvo.getFeedHeartYn())) {
            booleanBuilder.and(feedHeart.member.id.eq(pvo.getId()));
        }

        return jpaQueryFactory
                .select(new QFeedRVO(
                        feed.feedNo.longValue(),
                        feed.title.max(),
                        Expressions.stringTemplate("GROUP_CONCAT({0}, '#')", feedHashtag.hashtag),
                        feed.useYn.max(),
                        feed.member.id.max(),
                        feed.member.name.max(),
                        feed.member.nickname.max(),
                        feed.member.sex.max(),
                        feed.member.tel.max(),
                        feed.member.oauthNo.max(),
                        feedHeart.feedHeartNo.countDistinct().intValue(),
                        feedComment.feedCommentNo.countDistinct().intValue(),
                        Expressions.stringTemplate("GROUP_CONCAT({0})", feedFile.fileName),
                        Expressions.stringTemplate("GROUP_CONCAT({0})", feedFile.fileExt),
                        feedKeep.feedKeepNo.max(),
                        new CaseBuilder()
                                .when(feedHeart.member.id.eq(pvo.getId())).then(feedHeart.feedHeartNo)
                                .otherwise(Expressions.nullExpression()).max())               // Feed Heart 좋아요 유무 체크
                )
                .from(feed)
                .join(feed.member, memberInfo)
                    .on(memberInfo.useYn.eq("Y"))                  // 작성자 유저 정보 (사용여부 Y인 유저만)
                .join(feed.feedFile, feedFile)                          // Upload 파일
                .leftJoin(feed.feedHeart, feedHeart)                    // Feed 좋아요
                .leftJoin(feed.feedComment, feedComment)                // Feed 댓글 수
                .leftJoin(feed.feedKeep, feedKeep)
                    .on(feedKeep.member.id.eq(pvo.getId()))             // Feed Keep 여부 체크
                .leftJoin(feed.feedHashtags, feedHashtag)               // Feed Hashtag
                .where(booleanBuilder)
                .groupBy(feed.feedNo)
                .fetch();
    }

    /**
     * Hashtag 조회
     * */
    public List<FeedHashtagRVO> selectSearchHashtagList(SearchPVO pvo) {
        QFeedHashtag feedHashtag = QFeedHashtag.feedHashtag;
        QFeed feed = QFeed.feed;
        QMemberInfo memberInfo = QMemberInfo.memberInfo;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if("2".equals(pvo.getSearchDivisionCode()) && !StringUtils.isNotEmpty(pvo.getSearchValue()))
            booleanBuilder.and(feedHashtag.hashtag.contains(pvo.getSearchValue()));

        return jpaQueryFactory
                .select(new QFeedHashtagRVO(
                        feedHashtag.hashtag,
                        feedHashtag.hashtagNo.count().intValue()
                ))
                .from(feedHashtag)
                .join(feedHashtag.feed, feed)
                    .on(feed.useYn.eq("Y"))
                .join(feed.member, memberInfo)
                    .on(memberInfo.useYn.eq("Y"))
                .where(booleanBuilder)
                .groupBy(feedHashtag.hashtag)
                .orderBy(feedHashtag.hashtagNo.count().desc())
                .fetch();
    }

}
