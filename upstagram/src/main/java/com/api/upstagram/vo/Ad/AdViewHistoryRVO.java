package com.api.upstagram.vo.Ad;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AdViewHistoryRVO {
    private Long adViewNo;
    private Long adNo;
    private String id;
    private String linkCountYn;
    private String link;
    private LocalDateTime viewDttm;
}
