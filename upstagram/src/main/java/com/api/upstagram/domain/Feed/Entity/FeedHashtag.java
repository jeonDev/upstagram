package com.api.upstagram.domain.Feed.Entity;

import com.api.upstagram.domain.BaseIdEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FEED_HASHTAG")
public class FeedHashtag extends BaseIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_NO")
    private Feed feed;

    private String hashtag;

    @Builder
    public FeedHashtag(Long hashtagNo, Feed feed, String hashtag) {
        this.hashtagNo = hashtagNo;
        this.feed = feed;
        this.hashtag = hashtag;
    }
}
