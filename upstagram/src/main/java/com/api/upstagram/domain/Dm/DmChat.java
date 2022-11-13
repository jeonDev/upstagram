package com.api.upstagram.domain.Dm;

import com.api.upstagram.domain.BaseEntity;
import com.api.upstagram.domain.MemberInfo.MemberInfo;
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
}
