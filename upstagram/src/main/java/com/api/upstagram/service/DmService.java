package com.api.upstagram.service;

import com.api.upstagram.domain.Dm.*;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Dm.DmChatPVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /*
    * DM 채팅방 입장
    * */
    @Transactional
    public DmChatRoom dmChatRoomSave(DmChatPVO pvo) {
        Optional<DmChatRoom> dmChatRoomUserOpt = dmChatRoomRepository.findByDmChatRoomUserCheck(pvo.getId(), pvo.getReceiveId());
        
        if(dmChatRoomUserOpt.isPresent()) {
            return dmChatRoomUserOpt.get();
        } else {
            Long dmChatRoomNo = dmChatRoomRepository.getDmChatRoomSeq();
            DmChatRoom dmChatRoom = DmChatRoom.builder()
                    .dmChatRoomNo(dmChatRoomNo)
                    .build();
            dmChatRoom = dmChatRoomRepository.save(dmChatRoom);

            List<DmChatRoomUser> list = new ArrayList<>();

            DmChatRoomUser dmChatRoomUser1 = DmChatRoomUser.builder()
                    .dmChatRoom(dmChatRoom)
                    .member(MemberInfo.builder().id(pvo.getId()).build())
                    .build();

            DmChatRoomUser dmChatRoomUser2 = DmChatRoomUser.builder()
                    .dmChatRoom(dmChatRoom)
                    .member(MemberInfo.builder().id(pvo.getReceiveId()).build())
                    .build();

            list.add(dmChatRoomUser1);
            list.add(dmChatRoomUser2);

            dmChatRoomUserRepository.saveAll(list);

            return dmChatRoom;
        }
    }
}
