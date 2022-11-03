package com.api.upstagram.vo.Ad;

import lombok.Data;

@Data
public class AdCompanyPVO {
    private Long adCompanyNo;
    private String adCompanyName;
    private String managerName;
    private String managerTel;
    private String addr;
    private String addrDetail;
    private String zipCd;
}
