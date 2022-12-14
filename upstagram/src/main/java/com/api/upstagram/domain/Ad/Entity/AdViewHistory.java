package com.api.upstagram.domain.Ad.Entity;

import com.api.upstagram.domain.BaseEntity;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.vo.Ad.AdViewHistoryRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "AD_VIEW_HISTORY")
public class AdViewHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adViewNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AD_NO")
    private Ad ad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    private String linkCountYn;

    private String link;

    private LocalDateTime viewDttm;

    @Builder
    public AdViewHistory(Long adViewNo, Ad ad, MemberInfo member, String linkCountYn, String link, LocalDateTime viewDttm) {
        this.adViewNo = adViewNo;
        this.ad = ad;
        this.member = member;
        this.linkCountYn = linkCountYn;
        this.link = link;
        this.viewDttm = viewDttm;
    }

    public AdViewHistoryRVO adViewHistoryToRVO() {
        return AdViewHistoryRVO.builder()
                .adViewNo(this.adViewNo)
                .adNo(this.adViewNo)
                .id(this.member.getId())
                .name(this.member.getName())
                .nickname(this.member.getNickname())
                .sex(this.member.getSex())
                .tel(this.member.getTel())
                .oauthNo(this.member.getOauthNo())
                .linkCountYn(this.linkCountYn)
                .link(this.link)
                .viewDttm(this.viewDttm)
                .build();
    }
}
