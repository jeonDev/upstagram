package com.api.upstagram.domain.Ad.Entity;

import com.api.upstagram.domain.BaseIdEntity;

import com.api.upstagram.vo.Ad.AdCompanyRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "AD_COMPANY")
public class AdCompany extends BaseIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adCompanyNo;

    private String adCompanyName;

    private String managerName;

    private String managerTel;
    
    private String addr;
    
    private String addrDetail;

    private String zipCd;

    @Builder
    public AdCompany(Long adCompanyNo, String adCompanyName, String managerName, String managerTel, String addr,
            String addrDetail, String zipCd) {
        this.adCompanyNo = adCompanyNo;
        this.adCompanyName = adCompanyName;
        this.managerName = managerName;
        this.managerTel = managerTel;
        this.addr = addr;
        this.addrDetail = addrDetail;
        this.zipCd = zipCd;
    }

    public AdCompanyRVO adCompanyToRVO() {
        return AdCompanyRVO.builder()
                .adCompanyNo(this.adCompanyNo)
                .adCompanyName(this.adCompanyName)
                .managerName(this.managerName)
                .managerTel(this.managerTel)
                .addr(this.addr)
                .addrDetail(this.addrDetail)
                .zipCd(this.zipCd)
                .build();
    }

}
