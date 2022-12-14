import { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import { useSelector } from "react-redux";
import { selectDmChatRoomList, createDmChatRoom, selectDmChatList } from "../../api/DmApi";
import { selectFollowList } from "../../api/FollowApi";
import isNotEmpty from "../../config/utils";
import FollowCard from "../Follow/FollowCard";
import DmCard from "./DmCard";
import DmMessage from "./DmMessage";
import DmRoomCard from "./DmRoomCard";
import utils from "../../config/utils";

const Dm = () => {
    const [followMember, setFollowMember] = useState([]);
    const [dmChatRoom, setDmChatRoom] = useState('');               // 선택된 DM Room No
    const [dmChatRoomList, setDmChatRoomList] = useState([]);       // DM 채팅방 리스트
    const [dmChatList, setDmChatList] = useState([]);               // Dm 채팅 목록
    const [receiveId, setReceiveId] = useState('');                         // 상대 ID
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

    const dmChatRoomGenerate = async (dmId) => {
        await createDmChatRoom(dmId)
            .then((response) => {
                dmRoomPart(response.data.dmChatRoomNo);
                setDmChatRoom(response.data.dmChatRoomNo);
            })
            .catch((error) => {
                console.log(error);
            })
    }

    // Follow 대상 Dm Room 생성 & 입장
    const followDmChatRoomCreate = async (dmId) => {
        setDmChatRoom('')       // DM 방번호 초기화
        setDmChatList([]);      // DM 내역 초기화
        setReceiveId(dmId);          // 상대 아이디 세팅
    }

    // Dm Room 입장
    const dmRoomPart = async (dmChatRoomNo) => {
        await selectDmChatList(dmChatRoomNo)
        .then((response) => {
            setDmChatList(response.data);   // DM 내역 세팅
            setReceiveId('');         // 상대 아이디 초기화
        })
        .catch((error) => {
            console.log(error);
        });
    }

    useEffect(() => {
        if(dmChatRoom === '' || dmChatRoom === null) return;

        dmRoomPart(dmChatRoom);
    } , [dmChatRoom])

    return (
        <div className="container bg-light" style={{height:'800px'}}>
            <div className="d-flex justify-content-between" style={{height:'100%'}}>

                {/* DM Chat Room 목록 */}
                <div className="col-sm-4 border w-50">

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
                        {/* Search */}
                        <div>

                        </div>

                        {/* 조회내역 */}
                        <div>

                        </div>
                    </div>
                </div>

                {/* DM */}
                <div className="col-sm-8 border position-relative w-50">
                    {/* 출력창 */}
                    
                    <Card variant="outlined" className="overflow-auto" style={{height: '95%'}}>
                        {
                            ( (!utils.isNotEmpty(receiveId) && utils.isNotEmpty(dmChatRoom))
                                || (dmChatList.length === 0 && dmChatRoom !== '')
                            ) &&
                            (
                                <div className="text-center w-100 m-auto h1">
                                    첫 메시지를 보내보세요.
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
                            receiveId={receiveId}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Dm;

const dmStyle ={
    height: '50%',
    overflowY: 'scroll',
    overflowX: 'hidden',
    MsOverflowStyle: 'none'
}