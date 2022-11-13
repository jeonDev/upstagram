package com.api.upstagram.domain.Dm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DmChatRoomRepository extends JpaRepository<DmChatRoom, Long> {
    @Query(value =
            "SELECT d" +
                    "  FROM DmChatRoom d" +
                    "  JOIN DmChatRoomUser d1 ON d1.dmChatRoom.dmChatRoomNo = d.dmChatRoomNo" +
                    "   AND d1.member.id = :id" +
                    "  JOIN DmChatRoomUser d2 ON d2.dmChatRoom.dmChatRoomNo = d.dmChatRoomNo" +
                    "   AND d2.member.id = :receiveId" )
    Optional<DmChatRoom> findByDmChatRoomUserCheck(@Param("id") String id, @Param("receiveId") String receiveId);

    @Query(nativeQuery = true,
    value = "SELECT IFNULL(MAX(dcr.DM_CHAT_ROOM_NO), 0) + 1 FROM DM_CHAT_ROOM dcr")
    Long getDmChatRoomSeq();
}
