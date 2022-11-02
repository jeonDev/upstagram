package com.api.upstagram.vo.Ad;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdRVO {
    private Long adNo;
    private Long adCompanyNo;
    private String adCompanyName;
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
