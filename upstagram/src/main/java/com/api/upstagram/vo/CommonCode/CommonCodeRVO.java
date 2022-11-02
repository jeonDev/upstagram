package com.api.upstagram.vo.CommonCode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonCodeRVO {
    private String commonCode;
    private String commonCodeName;
    private String commonType;
    private String useYn;
    private int srtOdr;
    private String codeDetail;
}
