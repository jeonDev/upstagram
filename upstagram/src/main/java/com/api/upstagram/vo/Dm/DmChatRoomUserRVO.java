package com.api.upstagram.vo.Dm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DmChatRoomUserRVO {
    private Long dmChatRoomNo;
    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
    private String oauthNo;
}
