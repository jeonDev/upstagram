package com.api.upstagram.vo.Ad;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AdViewHistoryRVO {
    private Long adViewNo;
    private Long adNo;
    private String linkCountYn;
    private String link;
    private LocalDateTime viewDttm;

    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;
}
