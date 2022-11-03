package com.api.upstagram.domain.Ad;

import com.api.upstagram.common.vo.BaseIdEntity;

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

}
