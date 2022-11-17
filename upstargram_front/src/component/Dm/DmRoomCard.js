import { Card } from "react-bootstrap";

const DmRoomCard = (props) => {
    const {dmChatRoomNo, name, nickname, onclickEvent} = props;
    return (
        <Card variant="outlined">
            <div className="row pointer" onClick={onclickEvent}>
                <div className="col-sm-2 align-self-center">
                    <img alt="이미지" width={'30px;'} src={'/images/mypage.jpg'}/>
                </div>
                <div className="col-sm-10 text-left align-self-center">
                    <div>
                        {nickname}
                    </div>
                    <div>
                        {name}
                    </div>
                </div>
            </div>
        </Card>
    );
}

export default DmRoomCard;