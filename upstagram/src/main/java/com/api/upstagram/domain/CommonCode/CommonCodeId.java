package com.api.upstagram.domain.CommonCode;

import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonCodeId implements Serializable {

    private String commonCode;

    private String commonType;
}
