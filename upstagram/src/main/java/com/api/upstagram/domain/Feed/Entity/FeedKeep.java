package com.api.upstagram.domain.Feed.Entity;

import com.api.upstagram.domain.BaseEntity;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.vo.Feed.FeedKeepRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FEED_KEEP")
public class FeedKeep extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedKeepNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_NO")
    private Feed feed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    @Builder
    public FeedKeep(Long feedKeepNo, Feed feed, MemberInfo member) {
        this.feedKeepNo = feedKeepNo;
        this.feed = feed;
        this.member = member;
    }

    public FeedKeepRVO feedKeepToRVO() {
        return FeedKeepRVO.builder()
                .feedKeepNo(this.feedKeepNo)
                .feedNo(this.feed.getFeedNo())
                .id(this.member.getId())
                .name(this.member.getName())
                .nickname(this.member.getNickname())
                .sex(this.member.getSex())
                .tel(this.member.getTel())
                .oauthNo(this.member.getOauthNo())
                .build();
    }
}
