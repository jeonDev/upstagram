package com.api.upstagram.common.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfo {
    private String fileName;
    private String fileOriginName;
    private String fileExt;
    private String fileDiv;
}
