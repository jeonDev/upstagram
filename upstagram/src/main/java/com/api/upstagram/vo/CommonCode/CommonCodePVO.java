package com.api.upstagram.vo.CommonCode;

import lombok.Data;

@Data
public class CommonCodePVO {
    private String commonCode;
    private String commonCodeName;
    private String commonType;
    private String useYn;
    private int srtOdr;
    private String codeDetail;
}
