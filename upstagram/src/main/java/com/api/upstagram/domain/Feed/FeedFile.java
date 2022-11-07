package com.api.upstagram.domain.Feed;

import com.api.upstagram.common.vo.BaseIdEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FEED_FILE")
public class FeedFile extends BaseIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedFileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FEED_NO")
    private Feed feed;

    private String fileName;

    private String fileDiv;

    private String fileExt;

    private int sortOrder;

    @Builder
    public FeedFile(Long feedFileNo, Feed feed, String fileName, String fileDiv, String fileExt, int sortOrder) {
        this.feedFileNo = feedFileNo;
        this.feed = feed;
        this.fileName = fileName;
        this.fileDiv = fileDiv;
        this.fileExt = fileExt;
        this.sortOrder = sortOrder;
    }
}
