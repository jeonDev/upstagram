package com.api.upstagram.domain.CommonCode;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(CommonCodeId.class)
@Entity
@Table(name = "COMMON_CODE")
public class CommonCode {

    @Id
    private String commonCode;

    @Id
    private String commonType;

    private String commonCodeName;

    private String useYn;

    private int srtOdr;

    private String codeDetail;

    @Builder
    public CommonCode(String commonCode, String commonType, String commonCodeName, String useYn, int srtOdr, String codeDetail) {
        this.commonCode = commonCode;
        this.commonType = commonType;
        this.commonCodeName = commonCodeName;
        this.useYn = useYn;
        this.srtOdr = srtOdr;
        this.codeDetail = codeDetail;
    }
}
