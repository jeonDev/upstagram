package com.api.upstagram.vo.Ad;

import lombok.Data;

@Data
public class AdPVO {
    private Long adNo;
    private Long adCompanyNo;
    private String adName;
    private String useYn;
    private String costDiv;
    private int timeCountCost;
    private int viewCountCost;
    private int linkCountCost;
    private String linkUseYn;
    private String link;

}
