import { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import { selectDmChatRoomList, createDmChatRoom, selectDmChatList } from "../../api/DmApi";
import { selectFollowList } from "../../api/FollowApi";
import FollowCard from "../Follow/FollowCard";
import DmCard from "./DmCard";
import DmMessage from "./DmMessage";
import DmRoomCard from "./DmRoomCard";

const DmList = () => {
    const [followMember, setFollowMember] = useState([]);
    const [dmChatRoom, setDmChatRoom] = useState('');               // 선택된 DM Room No
    const [dmChatRoomList, setDmChatRoomList] = useState([]);       // DM 채팅방 리스트
    const [dmChatList, setDmChatList] = useState([]);               // Dm 채팅 목록

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
                setDmChatRoomList(response.data);
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
        await selectDmChatList(dmChatRoomNo)
        .then((response) => {
            setDmChatList(response.data);
        })
        .catch((error) => {

        });
    }

    useEffect(() => {
        if(dmChatRoom === '' || dmChatRoom === null) return;

        dmRoomPart(dmChatRoom);
    } , [dmChatRoom])

    return (
        <div className="container bg-light" style={{height:'100%'}}>
            <div className="d-flex justify-content-between">

                {/* DM Chat Room 목록 */}
                <div className="col-sm-4 border p-1">

                    {/* 채팅방 목록 */}
                    <div style={dmStyle}>
                        {dmChatRoomList.map( (room, idx) => (
                            <div key={idx}>
                                <DmRoomCard
                                    room={room}
                                    dmChatRoom={dmChatRoom}
                                    setDmChatRoom={setDmChatRoom}
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
                <div className="col-sm-8 border p-1 position-relative">
                    {/* 출력창 */}
                    
                    <Card variant="outlined" style={{height: '85%'}}>
                        {
                            dmChatList.map( (dm, idx) => (
                                <DmCard
                                    key={idx}
                                    dm={dm}
                                />
                            ))
                        }
                    </Card>
                    

                    {/* 입력창 */}
                    <div className="position-absolute bottom-0 w-100" >
                        <DmMessage
                            dmChatRoom={dmChatRoom}
                            dmChatList={dmChatList}
                            setDmChatList={setDmChatList}
                        />
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