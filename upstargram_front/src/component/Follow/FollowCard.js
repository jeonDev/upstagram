const FollowCard = (props) => {
    const {followYn, id, name, nickname} = props;
    return (
        <>
            <div className="d-flex justify-content-between">
                <div>
                    <img alt="이미지" width={'30px;'} src={'/images/mypage.jpg'}/>
                </div>
                <div className="text-left">
                    <div>
                        {nickname}
                    </div>
                    <div>
                        {name}
                    </div>
                </div>
                <div>
                    <button className="btn btn-dark">팔로우</button>
                </div>
            </div>
        </>
    );
}

export default FollowCard;