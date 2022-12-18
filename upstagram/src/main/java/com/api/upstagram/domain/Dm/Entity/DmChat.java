package com.api.upstagram.domain.Dm.Entity;

import com.api.upstagram.domain.BaseEntity;
import com.api.upstagram.domain.MemberInfo.Entity.MemberInfo;
import com.api.upstagram.vo.Dm.DmChatRVO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "DM_CHAT")
public class DmChat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dmChatNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DM_CHAT_ROOM_NO")
    private DmChatRoom dmChatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private MemberInfo member;

    private String message;

    private String viewYn;

    @Builder
    public DmChat(Long dmChatNo, DmChatRoom dmChatRoom, MemberInfo member, String message, String viewYn) {
        this.dmChatNo = dmChatNo;
        this.dmChatRoom = dmChatRoom;
        this.member = member;
        this.message = message;
        this.viewYn = viewYn;
    }

    public DmChatRVO dmChatToRVO() {
        return DmChatRVO.builder()
                .dmChatNo(this.dmChatNo)
                .dmChatRoomNo(this.dmChatRoom.getDmChatRoomNo())
                .message(this.message)
                .viewYn(this.viewYn)
                .sendId(this.member.getId())
                .sendName(this.member.getName())
                .sendNickname(this.member.getNickname())
                .sendSex(this.member.getSex())
                .sendTel(this.member.getTel())
                .sendOauthNo(this.member.getOauthNo())
                .build();
    }
}
