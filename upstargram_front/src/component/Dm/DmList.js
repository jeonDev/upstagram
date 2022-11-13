import { useEffect, useState } from "react";
import { selectDmChatRoomList, createDmChatRoom } from "../../api/DmApi";
import { selectFollowList } from "../../api/FollowApi";
import FollowCard from "../Follow/FollowCard";

const DmList = () => {
    const [followMember, setFollowMember] = useState([]);
    const [dmChatRoom, setDmChatRoom] = useState([]);

    useEffect(() => {
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
    
    const followDmChatRoomCreate = async (dmId) => {
        await createDmChatRoom(dmId)
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            })

    }

    return (
        <div className="container">
            <div className="d-flex justify-content-between bg-light">
                {/* DM Chat Room 목록 */}
                <div className="col-sm-4">
                    {/* 채팅방 목록 */}
                    <div style={dmStyle}>

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
                <div className="col-sm-8">
                    {/* 출력창 */}
                    <div>

                    </div>
                    {/* 입력창 */}
                    <div>

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