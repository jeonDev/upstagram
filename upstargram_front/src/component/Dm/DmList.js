import { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import { useSelector } from "react-redux";
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
    const id = useSelector(state => state.id);                      // Login ID

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
            console.log(response);
            setDmChatList(response.data);   // DM 내역 세팅
        })
        .catch((error) => {
            console.log(error);
        });
    }

    useEffect(() => {
        console.log(dmChatRoom);
        if(dmChatRoom === '' || dmChatRoom === null) return;

        dmRoomPart(dmChatRoom);
    } , [dmChatRoom])

    return (
        <div className="container bg-light" style={{height:'800px'}}>
            <div className="d-flex justify-content-between" style={{height:'100%'}}>

                {/* DM Chat Room 목록 */}
                <div className="col-sm-4 border">

                    {/* 채팅방 목록 */}
                    <div style={dmStyle}>
                        {dmChatRoomList.map( (room, idx) => (
                            <div key={idx} className="mb-1">
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
                            <div key={idx} className="mb-1">
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
                    
                    <Card variant="outlined" className="overflow-auto" style={{height: '95%'}}>
                        {
                            dmChatList.length === 0 && (
                                <div className="text-center w-100 m-auto h1">
                                    메시지를 보내세요.
                                </div>
                            )
                        }
                        {
                            dmChatList.map( (dm, idx) => (
                                <DmCard
                                    key={idx}
                                    dm={dm}
                                    id={id}
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
    height: '50%',
    overflowY: 'scroll',
    overflowX: 'hidden',
    MsOverflowStyle: 'none'
}