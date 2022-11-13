package com.api.upstagram.domain.Dm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DmChatRoomUserRepository extends JpaRepository<DmChatRoomUser, Long> {
    @Query(value =
            "SELECT dcu, d" +
            "  FROM DmChatRoomUser dcu" +
            "  JOIN DmChatRoom d ON d.dmChatRoomNo = dcu.dmChatRoom.dmChatRoomNo" +
            "  JOIN DmChat dc ON dc.dmChatRoom.dmChatRoomNo = d.dmChatRoomNo" +
            " WHERE dcu.member.id = :id")
    List<DmChatRoomUser> findByDmChatRoomList(@Param("id") String id);
}
