package com.api.upstagram.vo.Dm;

import lombok.Data;

@Data
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

    private String receiveId;
    private String receiveName;
    private String receiveNickname;
    private String receiveSex;
    private String receiveTel;
    private String receiveOauthNo;
}
