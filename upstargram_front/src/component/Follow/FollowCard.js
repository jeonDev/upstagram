import { Card } from "react-bootstrap";

const FollowCard = (props) => {
    const {followYn, id, name, nickname, onclickEvent} = props;
    return (
        <Card variant="outlined" >
            <div className="row" onClick={onclickEvent}>
                <div className="col-sm-2 align-self-center pointer">
                    <img alt="이미지" width={'30px;'} src={'/images/mypage.jpg'}/>
                </div>
                <div className="col-sm-6 text-left align-self-center pointer">
                    <div>
                        {nickname}
                    </div>
                    <div>
                        {name}
                    </div>
                </div>
                <div className="col-sm-4 align-self-center">
                    <button className="btn btn-dark pointer">팔로우</button>
                </div>
            </div>
        </Card>
    );
}

export default FollowCard;