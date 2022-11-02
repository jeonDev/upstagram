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
@Table(name = "AD_COMPANY")
public class AdCompany extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adCompanyNo;

    private String adCompanyName;

    @Builder
    public AdCompany(Long adCompanyNo, String adCompanyName) {
        this.adCompanyNo = adCompanyNo;
        this.adCompanyName = adCompanyName;
    }
}
