import { Card } from "react-bootstrap";

const DmRoomCard = (props) => {
    
    const {room, dmChatRoom, setDmChatRoom} = props;

    const dmChatRoomClick = () => {
        setDmChatRoom(room.dmChatRoomNo)
    }

    return (
        <Card variant="outlined" className="pointer">
            <div className={room.dmChatRoomNo === dmChatRoom ? 'row select-dm-room' : 'row'} onClick={dmChatRoomClick}>
                <div className="col-sm-2 align-self-center">
                    <img alt="이미지" width={'30px;'} src={'/images/mypage.jpg'}/>
                </div>
                <div className="col-sm-10 text-left align-self-center">
                    <div>
                        {room.nickname}
                    </div>
                    <div>
                        {room.name}
                    </div>
                </div>
            </div>
        </Card>
    );
}

export default DmRoomCard;