import { useEffect, useState } from "react";
import { selectDmChatRoomList, createDmChatRoom } from "../../api/DmApi";
import { selectFollowList } from "../../api/FollowApi";
import FollowCard from "../Follow/FollowCard";
import DmRoomCard from "./DmRoomCard";

const DmList = () => {
    const [followMember, setFollowMember] = useState([]);
    const [dmChatRoom, setDmChatRoom] = useState([]);

    useEffect(() => {
        dmChatRoomSearch();
        followSearch();
    }, []);

    // Follow 조회
    const followSearch = async () => {
        await selectFollowList()
            .then((response) => {
                setFollowMember(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    const dmChatRoomSearch = async () => {
        await selectDmChatRoomList()
            .then((response) => {
                setDmChatRoom(response.data);
            })
            .catch((error) => {
                console.log(error);
            })
    }
    
    // Follow 대상 Dm Room 생성 & 입장
    const followDmChatRoomCreate = async (dmId) => {
        await createDmChatRoom(dmId)
            .then((response) => {
                dmRoomPart(response.dmChatRoomNo);
            })
            .catch((error) => {
                console.log(error);
            })
    }

    // Dm Room 입장
    const dmRoomPart = async (dmChatRoomNo) => {

    }

    return (
        <div className="container bg-light">
            <div className="d-flex justify-content-between">
                {/* DM Chat Room 목록 */}
                <div className="col-sm-4 border p-1">
                    {/* 채팅방 목록 */}
                    <div style={dmStyle}>
                        {dmChatRoom.map( (room, idx) => (
                            <div key={idx}>
                                <DmRoomCard
                                    dmChatRoomNo={room.dmChatRoomNo}
                                    name={room.name}
                                    nickname={room.nickname}
                                    onclickEvent={() => dmRoomPart(room.dmChatRoomNo)}
                                />
                            </div>
                        ))}
                    </div>
                    {/* Follow */}
                    <div style={dmStyle}>
                        {followMember.map( (follow, idx) => (
                            <div key={idx}>
                                <FollowCard
                                    followYn={'Y'}
                                    id={follow.followId}
                                    name={follow.followName}
                                    nickname={follow.followNickname}
                                    onclickEvent={() => followDmChatRoomCreate(follow.followId)}
                                />
                            </div>
                        ))}
                    </div>
                </div>

                {/* DM */}
                <div className="col-sm-8 border p-1">
                    {/* 출력창 */}
                    <div className="border">

                    </div>
                    {/* 입력창 */}
                    <div className="row">
                        <div className="col-sm-9">
                            <input type="text" className="form-control" name="message" id="message"/>
                        </div>
                        <div className="col-sm-3">
                            <button className="btn btn-outline-primary btn-block w-100">전송</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DmList;

const dmStyle ={
    height: '150px',
    overflowY: 'scroll',
    MsOverflowStyle: 'none'
}