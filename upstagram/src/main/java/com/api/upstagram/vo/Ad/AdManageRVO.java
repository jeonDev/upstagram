package com.api.upstagram.vo.Ad;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdManageRVO {
    private Long adManageNo;
    private String startDttm;
    private String endDttm;
    private String startTime;
    private String endTime;

    private Long adNo;
    private Long adCompanyNo;
    private String adName;
    private String adFileName;
    private String useYn;
    private String fileExt;
    private String costDiv;
    private int timeCountCost;
    private int viewCountCost;
    private int linkCountCost;
    private String linkUseYn;
    private String link;
}
