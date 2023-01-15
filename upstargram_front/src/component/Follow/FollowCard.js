import { Card } from "react-bootstrap";
import { deleteFollowUser, insertFollowUser } from "../../api/FollowApi";

const FollowCard = (props) => {
    const {followYn, memberInfo, onclickEvent} = props;

    // Follow 요청
    const requestFollow = async (id) => {
        const result = await insertFollowUser(id);

        alert(result.message);
    }

    const cancelFollow = async (followNo) => {
        const result = await deleteFollowUser(followNo);

        alert(result.message);
    }
    return (
        <Card variant="outlined" >
            <div className="row">
                <div className="col-sm-2 align-self-center pointer text-center" onClick={onclickEvent}>
                    <img alt="이미지" width={'30px;'} src={memberInfo.profileImage != null ? process.env.REACT_APP_SERVER_FILE_URL + memberInfo.profileImage : '/images/mypage.jpg'}/>
                </div>
                <div className="col-sm-7 text-left align-self-center pointer" onClick={onclickEvent}>
                    <div>
                        {memberInfo.nickname}
                    </div>
                    <div>
                        {memberInfo.name}
                    </div>
                </div>
                {
                followYn === 'Y'
                &&
                <div className="col-sm-3 align-self-center text-center">
                    {
                        memberInfo.followNo === 0
                        ?
                        <button className="btn btn-dark pointer" onClick={() => requestFollow(memberInfo.id)}>팔로우</button>
                        :
                        <button className="btn btn-secondary pointer" onClick={() => cancelFollow(memberInfo.followNo)}>팔로우</button>
                    }
                    
                </div>
                }
            </div>
        </Card>
    );
}

export default FollowCard;