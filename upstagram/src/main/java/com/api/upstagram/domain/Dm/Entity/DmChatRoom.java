package com.api.upstagram.domain.Dm.Entity;

import com.api.upstagram.domain.BaseEntity;
import com.api.upstagram.vo.Dm.DmChatRoomUserRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DM_CHAT_ROOM")
public class DmChatRoom extends BaseEntity {

    @Id
    private Long dmChatRoomNo;

    @Builder
    public DmChatRoom(Long dmChatRoomNo) {
        this.dmChatRoomNo = dmChatRoomNo;
    }

    public DmChatRoomUserRVO dmChatRoomToRVO(){
        return DmChatRoomUserRVO.builder()
                .dmChatRoomNo(this.dmChatRoomNo)
                .build();
    }
}
