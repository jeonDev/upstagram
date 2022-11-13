package com.api.upstagram.common.socket;

import lombok.extern.slf4j.Slf4j;
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

    private static List<WebSocketSession> list = new ArrayList<>();

    /*
    * Message 전송
    * */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);

        for(WebSocketSession s : list) {
            s.sendMessage(message);
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
