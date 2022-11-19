package com.api.upstagram.domain.Dm;

import com.api.upstagram.vo.Dm.DmChatInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DmChatRepository extends JpaRepository<DmChat, Long> {

    @Query(value =
            "SELECT dc.dmChatNo AS dmChatNo" +
            "     , dc.dmChatRoom.dmChatRoomNo AS dmChatRoomNo" +
            "     , dc.message AS message" +
            "     , dc.viewYn AS viewYn" +
            "     , send.id AS sendId" +
            "     , send.name AS sendName" +
            "     , send.nickname AS sendNickname" +
            "     , send.sex AS sendSex" +
            "     , send.tel AS sendTel" +
            "     , send.oauthNo AS sendOauthNo" +
            "  FROM DmChat dc" +
            "  JOIN MemberInfo send ON dc.member.id = send.id" +
            " WHERE dc.dmChatRoom.dmChatRoomNo = :dmChatRoomNo"
    )
    List<DmChatInterface> selectDmChatList(@Param("dmChatRoomNo") Long dmChatRoomNo);
}
