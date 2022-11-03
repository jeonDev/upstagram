package com.api.upstagram.vo.Ad;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdCompanyRVO {
    private Long adCompanyNo;
    private String adCompanyName;
    private String managerName;
    private String managerTel;
    private String addr;
    private String addrDetail;
    private String zipCd;
}
