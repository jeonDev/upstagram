package com.api.upstagram.domain.Feed;

import com.api.upstagram.common.vo.BaseEntity;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Feed.FeedCommentRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FEED_COMMENT_HEART")
public class FeedCommentHeart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedCommentHeartNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_COMMENT_NO")
    private FeedComment feedComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    @Builder
    public FeedCommentHeart(Long feedCommentHeartNo, FeedComment feedComment, MemberInfo member) {
        this.feedCommentHeartNo = feedCommentHeartNo;
        this.feedComment = feedComment;
        this.member = member;
    }

    public FeedCommentRVO feedCommentToRVO(){
        return FeedCommentRVO.builder()
                .feedCommentNo(this.feedComment.getFeedCommentNo())
                //.id(this.member.getId())
                .content(this.feedComment.getContent())
                .useYn(this.feedComment.getUseYn())
                .topFix(this.feedComment.getTopFix())
                .feedCommentHeartNo(this.feedCommentHeartNo)
                .commentHeartId(this.member.getId())
                .build();
    }
}
