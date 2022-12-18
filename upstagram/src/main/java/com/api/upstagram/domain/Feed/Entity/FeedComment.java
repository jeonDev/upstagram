package com.api.upstagram.domain.Feed.Entity;

import com.api.upstagram.domain.BaseIdEntity;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.vo.Feed.FeedCommentRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FEED_COMMENT")
public class FeedComment extends BaseIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedCommentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_NO")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    private String content;

    private String useYn;

    private String topFix;

    @Builder
    public FeedComment(Long feedCommentNo, Feed feed, MemberInfo member, String content, String useYn, String topFix) {
        this.feedCommentNo = feedCommentNo;
        this.feed = feed;
        this.member = member;
        this.content = content;
        this.useYn = useYn;
        this.topFix = topFix;
    }

    public FeedCommentRVO feedCommentToRVO() {
        return FeedCommentRVO.builder()
                .feedCommentNo(this.feedCommentNo)
                .id(this.member.getId())
                .content(this.content)
                .useYn(this.useYn)
                .topFix(this.topFix)
                .build();
    }
}
