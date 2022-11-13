package com.api.upstagram.service;

import com.api.upstagram.domain.Dm.*;
import com.api.upstagram.vo.Dm.DmChatPVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DmService {

    private final DmChatRepository dmChatRepository;

    private final DmChatRoomRepository dmChatRoomRepository;

    private final DmChatRoomUserRepository dmChatRoomUserRepository;

    /*
    * Dm 내역 가져오기
    * */
    public List<DmChat> selectDmChatList(DmChatPVO pvo) {
        return null;
    }

    /*
    * Dm 채팅방 목록 가져오기
    * */
    public List<DmChatRoom> selectDmChatRoomList(DmChatPVO pvo) {
        return null;
    }
}
