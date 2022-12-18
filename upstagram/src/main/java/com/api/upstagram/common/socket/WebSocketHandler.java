package com.api.upstagram.common.socket;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.domain.Dm.Entity.DmChat;
import com.api.upstagram.domain.Dm.Entity.DmChatRoom;
import com.api.upstagram.domain.Dm.Entity.DmChatRoomUser;
import com.api.upstagram.service.DmService;
import com.api.upstagram.vo.Dm.DmChatPVO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private DmService dmService;
    private static List<WebSocketSession> list = new ArrayList<>();

    /*
     * Message 전송
     * */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        log.info("payload : " + payload);

        JSONObject obj = CommonUtils.JsonToObjectParser(payload);
        String type = StringUtils.nullToBlank((String) obj.get("type"));

        if(!StringUtils.isNotEmpty(type)) {
            if("1".equals(type)) {
                String receiveId = StringUtils.nullToBlank((String) obj.get("receiveId"));
                String dmChatRoomNo = StringUtils.nullToBlank((String) obj.get("dmChatRoomNo"));
                String dmMessage = StringUtils.nullToBlank((String) obj.get("message"));

                DmChatPVO pvo = DmChatPVO.builder()
                        .id(CommonUtils.getUserId())
                        .receiveId(receiveId)
                        .message(dmMessage)
                        .dmChatRoomNo(Long.parseLong(dmChatRoomNo))
                        .build();
                // DM 전송
                DmChat dmChat = dmService.dmSend(pvo);

                if(dmChat != null) {
                    DmChatRoom dmChatRoom = DmChatRoom.builder()
                            .dmChatRoomNo(dmChat.getDmChatRoom().getDmChatRoomNo())
                            .build();

                    List<DmChatRoomUser> dmChatRoomUsers = dmService.selectDmChatRoomUser(dmChatRoom);

                    for(WebSocketSession s : list) {
                        if(dmChatRoomUsers.contains(s.getId())) {
                            s.sendMessage(message);

                        }
                    }
                }

            }
        }

    }

    /*
     * Client 접속 시 호출되는 메서드
     * */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Client 접속[" + session + "]");
        list.add(session);
    }

    /*
     * Client 접속 해제 시 호출되는 메서드
     * */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Client 접속 해제[" + session + "]");
        list.remove(session);
    }
}