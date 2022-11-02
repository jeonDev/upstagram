package com.api.upstagram.domain.Ad;

import com.api.upstagram.common.vo.BaseEntity;
import lombok.AccessLevel;
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

}
