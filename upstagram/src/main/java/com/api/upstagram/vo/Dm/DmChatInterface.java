package com.api.upstagram.vo.Dm;

public interface DmChatInterface {
    Long getDmChatNo();

    Long dmChatRoomNo();

    String getMessage();
    String getViewYn();

    String getSendId();
    String getSendName();
    String getSendNickname();
    String getSendSex();
    String getSendTel();
    String getSendOauthNo();
}
