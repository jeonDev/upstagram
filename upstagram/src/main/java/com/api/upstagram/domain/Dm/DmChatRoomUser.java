package com.api.upstagram.domain.Dm;

import com.api.upstagram.domain.BaseEntity;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
import com.api.upstagram.vo.Dm.DmChatRoomUserRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DM_CHAT_ROOM_USER")
public class DmChatRoomUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dmRoomUserNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DM_CHAT_ROOM_NO")
    private DmChatRoom dmChatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    @Builder
    public DmChatRoomUser(Long dmRoomUserNo, DmChatRoom dmChatRoom, MemberInfo member) {
        this.dmRoomUserNo = dmRoomUserNo;
        this.dmChatRoom = dmChatRoom;
        this.member = member;
    }

    public DmChatRoomUserRVO dmChatRoomUserToRVO(){
        return DmChatRoomUserRVO.builder()
                .dmChatRoomNo(this.dmChatRoom.getDmChatRoomNo())
                .id(this.member.getId())
                .name(this.member.getName())
                .nickname(this.member.getNickname())
                .tel(this.member.getTel())
                .sex(this.member.getSex())
                .oauthNo(this.member.getOauthNo())
                .build();
    }
}
