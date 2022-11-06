package com.api.upstagram.domain.Ad;

import com.api.upstagram.common.vo.BaseIdEntity;
import com.api.upstagram.vo.Ad.AdManageRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "AD_MANAGE")
public class AdManage extends BaseIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adManageNo;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "AD_NO")
    private Ad ad;

    private String startDttm;

    private String endDttm;

    private String startTime;

    private String endTime;

    @Builder
    public AdManage(Long adManageNo, Ad ad, String startDttm, String endDttm, String startTime, String endTime) {
        this.adManageNo = adManageNo;
        this.ad = ad;
        this.startDttm = startDttm;
        this.endDttm = endDttm;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public AdManageRVO adManageToRVO() {
        return AdManageRVO.builder()
                .adManageNo(this.adManageNo)
                .adNo(this.ad.getAdNo())
                .startDttm(this.startDttm)
                .endDttm(this.endDttm)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .adCompanyNo(this.ad.getAdCompany() != null ? this.ad.getAdCompany().getAdCompanyNo() : null)
                .adName(this.ad.getAdName())
                .adFileName(this.ad.getAdFileName())
                .useYn(this.ad.getUseYn())
                .fileExt(this.ad.getFileExt())
                .costDiv(this.ad.getCostDiv())
                .timeCountCost(this.ad.getTimeCountCost())
                .viewCountCost(this.ad.getViewCountCost())
                .linkCountCost(this.ad.getLinkCountCost())
                .linkUseYn(this.ad.getLinkUseYn())
                .link(this.ad.getLink())
                .build();
    }
}
