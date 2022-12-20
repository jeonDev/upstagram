package com.api.upstagram.domain.Feed.Repository;

import com.api.upstagram.domain.Feed.Entity.*;
import com.api.upstagram.domain.FollowUser.Entity.QFollowUser;
import com.api.upstagram.domain.MemberInfo.Entity.QMemberInfo;
import com.api.upstagram.vo.Feed.FeedRVO;
import com.api.upstagram.vo.Feed.QFeedRVO;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedDslRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;
    private QFeed feed = QFeed.feed;
    private QFollowUser followUser = QFollowUser.followUser;
    private QMemberInfo memberInfo = QMemberInfo.memberInfo;
    private QFeedFile feedFile = QFeedFile.feedFile;
    private QFeedHeart feedHeart = QFeedHeart.feedHeart;
    private QFeedHeart myFeedHeart = QFeedHeart.feedHeart;
    private QFeedComment feedComment = QFeedComment.feedComment;
    private QFeedKeep feedKeep = QFeedKeep.feedKeep;

    public FeedDslRepository(JPAQueryFactory jpaQueryFactory) {
        super(Feed.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * Feed List 조회
     * */
    public List<FeedRVO> selectFeedList(String id) {
        return jpaQueryFactory
                .select(new QFeedRVO(
                        feed.feedNo.longValue(),
                        feed.title.max(),
                        feed.hashtag.max(),
                        feed.useYn.max(),
                        feed.member.id.max(),
                        feed.member.name.max(),
                        feed.member.nickname.max(),
                        feed.member.sex.max(),
                        feed.member.tel.max(),
                        feed.member.oauthNo.max(),
                        feedHeart.feedHeartNo.count().intValue(),
                        feedComment.feedCommentNo.count().intValue(),
                        Expressions.stringTemplate("GROUP_CONCAT({0})", feedFile.fileName),
                        Expressions.stringTemplate("GROUP_CONCAT({0})", feedFile.fileExt),
                        feedKeep.feedKeepNo.max().as("feedKeepNo"),
                        myFeedHeart.feedHeartNo.max().as("feedHeartNo")))
                .from(feed)
                .join(feed.member, memberInfo)
                    .on(memberInfo.useYn.eq("Y"))              // 작성자 유저 정보 (사용여부 Y인 유저만)
                .join(memberInfo.followUser, followUser)
                    .on(followUser.idMember.id.eq(id))          // 내가 Follow 한 멤버의 Feed
                .join(feed.feedFile, feedFile)                      // Upload 파일
                .leftJoin(feed.feedHeart, feedHeart)                // Feed 좋아요
                .leftJoin(feed.feedHeart, myFeedHeart)
                    .on(myFeedHeart.member.id.eq(id))               // Feed 좋아요 유무 체크
                .leftJoin(feed.feedComment, feedComment)            // Feed 댓글 수
                .leftJoin(feed.feedKeep, feedKeep)
                    .on(feedKeep.member.id.eq(id))                  // Feed Keep 여부 체크
                .where(feed.useYn.eq("Y"))                     // Feed 사용여부 체크
                .groupBy(feed.feedNo)
                .fetch();
    }

}
