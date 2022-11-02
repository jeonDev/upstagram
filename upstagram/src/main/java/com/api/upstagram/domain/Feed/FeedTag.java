package com.api.upstagram.domain.Feed;

import com.api.upstagram.common.vo.BaseEntity;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FEED_TAG")
public class FeedTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedTagNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_NO")
    private Feed feed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAG_ID")
    private MemberInfo member;

    @Builder
    public FeedTag(Long feedTagNo, Feed feed, MemberInfo member) {
        this.feedTagNo = feedTagNo;
        this.feed = feed;
        this.member = member;
    }
}
