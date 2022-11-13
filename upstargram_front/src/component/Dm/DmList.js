import { useEffect, useState } from "react";
import { selectDmChatRoomList } from "../../api/DmApi";
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

    return (
        <div className="container">
            
            <div className="d-flex justify-content-between">
                {/* DM Chat Room 목록 */}
                <div className="col-sm-6">
                    {/* 채팅방 목록 */}
                    <div className="h-50">

                    </div>
                    {/* Follow */}
                    <div className="h-50">
                        {followMember.map( (follow, idx) => (
                            <div key={idx}>
                                <FollowCard
                                    followYn={'Y'}
                                    id={follow.followId}
                                    name={follow.followName}
                                    nickname={follow.followNickname}
                                />
                            </div>
                        ))}
                    </div>
                </div>

                {/* DM */}
                <div className="col-sm-6">

                </div>
            </div>
        </div>
    );
}

export default DmList;