package com.api.upstagram.domain.Ad;

import com.api.upstagram.common.vo.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "AD")
public class Ad extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AD_COMPANY_NO")
    private AdCompany adCompany;

    private String adName;

    private String adFileName;

    private String useYn;

    private String fileDiv;

    private String fileExt;

    private String costDiv;

    private int timeCountCost;

    private int viewCountCost;

    private int linkCountCost;

    private String linkUseYn;

    private String link;

    @Builder
    public Ad(Long adNo, AdCompany adCompany, String adName, String adFileName, String useYn, String fileDiv, String fileExt, String costDiv, int timeCountCost, int viewCountCost, int linkCountCost, String linkUseYn, String link) {
        this.adNo = adNo;
        this.adCompany = adCompany;
        this.adName = adName;
        this.adFileName = adFileName;
        this.useYn = useYn;
        this.fileDiv = fileDiv;
        this.fileExt = fileExt;
        this.costDiv = costDiv;
        this.timeCountCost = timeCountCost;
        this.viewCountCost = viewCountCost;
        this.linkCountCost = linkCountCost;
        this.linkUseYn = linkUseYn;
        this.link = link;
    }
}