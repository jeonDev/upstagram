import { Card } from "react-bootstrap";
import { insertFollowUser } from "../../api/FollowApi";

const FollowCard = (props) => {
    const {followYn, id, name, nickname, profileImage, onclickEvent} = props;

    // TODO: Follow 여부 체크해서 버튼 변경

    // Follow 요청
    const requestFollow = async (id) => {
        const result = await insertFollowUser(id);

        alert(result.message);
    }
    return (
        <Card variant="outlined" >
            <div className="row" onClick={onclickEvent}>
                <div className="col-sm-2 align-self-center pointer text-center">
                    <img alt="이미지" width={'30px;'} src={profileImage != null ? process.env.REACT_APP_SERVER_FILE_URL + profileImage : '/images/mypage.jpg'}/>
                </div>
                <div className="col-sm-6 text-left align-self-center pointer">
                    <div>
                        {nickname}
                    </div>
                    <div>
                        {name}
                    </div>
                </div>
                {
                followYn === 'Y'
                &&
                <div className="col-sm-4 align-self-center text-right">
                    <button className="btn btn-dark pointer" onClick={() => requestFollow(id)}>팔로우</button>
                </div>
                }
            </div>
        </Card>
    );
}

export default FollowCard;