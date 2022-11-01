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
@Table(name = "FEED")
public class Feed extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    private String title;

    private String hashtag;

    private String useYn;

    // TODO: 파일 관계 어떻게할지 수정 OneToMany

    @Builder
    public Feed(Long feedNo, MemberInfo member, String title, String hashtag, String useYn) {
        this.feedNo = feedNo;
        this.member = member;
        this.title = title;
        this.hashtag = hashtag;
        this.useYn = useYn;
    }
}
