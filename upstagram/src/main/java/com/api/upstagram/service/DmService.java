package com.api.upstagram.service;

import com.api.upstagram.common.Exception.CustomException;
import com.api.upstagram.common.util.StringUtils;
import com.api.upstagram.common.vo.Response;
import com.api.upstagram.domain.Dm.*;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Dm.DmChatPVO;
import com.api.upstagram.vo.Dm.DmChatRVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<DmChatRVO> selectDmChatList(DmChatPVO pvo) {
        return dmChatRepository.selectDmChatList(pvo.getDmChatRoomNo())
                .stream()
                .map(m -> DmChatRVO.builder()
                        .dmChatNo(m.getDmChatNo())
                        .dmChatRoomNo(m.getDmChatNo())
                        .message(m.getMessage())
                        .viewYn(m.getViewYn())
                        .sendId(m.getSendId())
                        .sendName(m.getSendName())
                        .sendNickname(m.getSendNickname())
                        .sendSex(m.getSendSex())
                        .sendTel(m.getSendTel())
                        .sendOauthNo(m.getSendOauthNo())
                        .build())
                .collect(Collectors.toList());
    }

    /*
    * Dm 채팅방 목록 가져오기
    * */
    public List<DmChatRoomUser> selectDmChatRoomList(DmChatPVO pvo) {
        return dmChatRoomUserRepository.findByDmChatRoomList(pvo.getId());
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

    /*
    * Dm 전송
    * */
    public DmChat dmSend(DmChatPVO pvo) {
        if(pvo.getDmChatRoomNo() == null || pvo.getDmChatRoomNo() == 0) throw new CustomException(Response.DUPLICATION_ERROR.getCode(), Response.DUPLICATION_ERROR.getMessage());
        if(StringUtils.isNotEmpty(pvo.getId())) throw new CustomException(Response.DUPLICATION_ERROR.getCode(), Response.DUPLICATION_ERROR.getMessage());

        Optional<DmChatRoom> dmChatRoom = dmChatRoomRepository.findById(pvo.getDmChatRoomNo());
        MemberInfo member = MemberInfo.builder()
                .id(pvo.getId())
                .build();

        DmChat dmChat = DmChat.builder()
                .dmChatRoom(dmChatRoom.get())
                .member(member)
                .message(pvo.getMessage())
                .viewYn("N")
                .build();

        return dmChatRepository.save(dmChat);
    }
}
