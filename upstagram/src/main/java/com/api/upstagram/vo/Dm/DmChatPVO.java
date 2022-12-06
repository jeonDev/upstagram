package com.api.upstagram.vo.Dm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DmChatPVO {
    private String id;
    private String receiveId;
    private Long dmChatRoomNo;
    private String message;
}