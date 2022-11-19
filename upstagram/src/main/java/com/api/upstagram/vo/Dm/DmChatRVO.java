package com.api.upstagram.vo.Dm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DmChatRVO {
    private Long dmChatNo;

    private Long dmChatRoomNo;

    private String message;
    private String viewYn;

    private String sendId;
    private String sendName;
    private String sendNickname;
    private String sendSex;
    private String sendTel;
    private String sendOauthNo;
}
