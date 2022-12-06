package com.api.upstagram.domain.Dm;

import com.api.upstagram.vo.Dm.DmChatRoomUserInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DmChatRoomUserRepository extends JpaRepository<DmChatRoomUser, Long> {
    @Query(nativeQuery = true,
            value =
            "SELECT A.DM_CHAT_ROOM_NO AS dmChatRoomNo" +
            "     , target.ID AS id" +
            "     , m.NAME AS name" +
            "     , m.NICKNAME AS nickname" +
            "     , m.SEX AS sex" +
            "     , m.TEL AS tel" +
            "     , m.OAUTH_NO AS oauthNo" +
            "  FROM (" +
            "       SELECT dcr.DM_CHAT_ROOM_NO" +
            "            , MAX(dcru.DM_ROOM_USER_NO) AS DM_ROOM_USER_NO" +
            "            , MAX(dcru.ID) AS ID" +
            "         FROM DM_CHAT_ROOM_USER dcru" +
            "         JOIN DM_CHAT_ROOM dcr ON dcru.DM_CHAT_ROOM_NO = dcr.DM_CHAT_ROOM_NO" +
            "         JOIN DM_CHAT dc ON dcr.DM_CHAT_ROOM_NO = dc.DM_CHAT_ROOM_NO" +
            "        WHERE dcru.ID = :id" +
            "        GROUP BY dcr.DM_CHAT_ROOM_NO" +
            "       ) A" +
            "  JOIN DM_CHAT_ROOM_USER target ON A.DM_CHAT_ROOM_NO = target.DM_CHAT_ROOM_NO" +
            "   AND A.DM_ROOM_USER_NO <> target.DM_ROOM_USER_NO" +
            "  JOIN MEMBER_INFO m ON target.ID = m.ID" +
            "   AND m.USE_YN = 'Y'")
    List<DmChatRoomUserInterface> findByDmChatRoomList(@Param("id") String id);

    List<DmChatRoomUser> findAllByDmChatRoom(DmChatRoom dmChatRoom);
}
