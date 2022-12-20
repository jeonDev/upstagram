package com.api.upstagram.domain.Feed.Entity;

import com.api.upstagram.domain.BaseIdEntity;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.vo.Feed.FeedRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FEED")
public class Feed extends BaseIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    private String title;

    private String hashtag;

    private String useYn;

    @OneToMany(mappedBy = "feed")
    private List<FeedFile> feedFile;

    @OneToMany(mappedBy = "feed")
    private List<FeedHeart> feedHeart;

    @OneToMany(mappedBy = "feed")
    private List<FeedComment> feedComment;

    @OneToMany(mappedBy = "feed")
    private List<FeedKeep> feedKeep;

    @Builder
    public Feed(Long feedNo, MemberInfo member, String title, String hashtag, String useYn) {
        this.feedNo = feedNo;
        this.member = member;
        this.title = title;
        this.hashtag = hashtag;
        this.useYn = useYn;
    }


    public FeedRVO feedToRVO(){
        return FeedRVO.builder()
                .feedNo(this.feedNo)
                .title(this.title)
                .hashtag(this.hashtag)
                .useYn(this.useYn)
                .id(this.member.getId())
                .name(this.member.getName())
                .nickname(this.member.getNickname())
                .tel(this.member.getTel())
                .sex(this.member.getSex())
                .oauthNo(this.member.getOauthNo())
                .build();
    }
}
