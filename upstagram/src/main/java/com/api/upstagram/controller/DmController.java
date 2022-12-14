package com.api.upstagram.controller;

import com.api.upstagram.common.util.CommonUtils;
import com.api.upstagram.common.vo.ResponseVO;
import com.api.upstagram.service.DmService;
import com.api.upstagram.vo.Dm.DmChatPVO;
import com.api.upstagram.vo.Dm.DmChatRVO;
import com.api.upstagram.vo.Dm.DmChatRoomUserRVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class DmController {
    @Autowired
    private DmService dmService;

    /*
    * DM 내역 가져오기
    * */
    @GetMapping("/user/dm/list/{dmChatRoomNo}")
    public ResponseVO<List<DmChatRVO>> dmChatList(@PathVariable("dmChatRoomNo") String dmChatRoomNo) {
        log.info(this.getClass().getName() + " ==> Dm 내역 가져오기");
        ResponseVO<List<DmChatRVO>> r = new ResponseVO<List<DmChatRVO>>();
        DmChatPVO pvo = new DmChatPVO();
        pvo.setDmChatRoomNo(Long.parseLong(dmChatRoomNo));

        List<DmChatRVO> rvo = dmService.selectDmChatList(pvo);

        r.setData(rvo);

        return r;
    }

    /*
    * DM 내역 등록
    * */

    /*
    * Dm 채팅방 목록 가져오기
    * */
    @GetMapping("/user/dm/room/list")
    public ResponseVO<List<DmChatRoomUserRVO>> dmChatRoomList() {
        log.info(this.getClass().getName() + " ==> Dm 채팅방 목록 가져오기");
        ResponseVO<List<DmChatRoomUserRVO>> r = new ResponseVO<List<DmChatRoomUserRVO>>();

        DmChatPVO pvo = new DmChatPVO();
        pvo.setId(CommonUtils.getUserId());

        List<DmChatRoomUserRVO> rvo = dmService.selectDmChatRoomList(pvo);

        r.setData(rvo);

        return r;
    }

    /*
    * Dm 채팅방 입장
    * */
    @PostMapping("/user/dm/room/create/{id}")
    public ResponseVO<DmChatRoomUserRVO> dmRoomCreate(@PathVariable("id") String id){
        log.info(this.getClass().getName() + " ==> DM 채팅방 입장(" + id + ")");
        ResponseVO<DmChatRoomUserRVO> r = new ResponseVO<DmChatRoomUserRVO>();
        DmChatPVO pvo = new DmChatPVO();
        pvo.setId(CommonUtils.getUserId());
        pvo.setReceiveId(id);

        DmChatRoomUserRVO rvo = dmService.dmChatRoomSave(pvo).dmChatRoomToRVO();
                
        r.setData(rvo);
        
        return r;
    }
    
    /*
    * DM 채팅방 나가기
    * */


    /*
    * Dm 보내기
    * */
    @PostMapping("/user/dm/send")
    public ResponseVO<DmChatRVO> sendDm(@RequestBody DmChatPVO pvo){
        log.info(this.getClass().getName() + " ==> DM Send");
        ResponseVO<DmChatRVO> r = new ResponseVO<DmChatRVO>();
        pvo.setId(CommonUtils.getUserId());

        DmChatRVO rvo = dmService.dmSend(pvo).dmChatToRVO();

        r.setData(rvo);

        return r;
    }
}
