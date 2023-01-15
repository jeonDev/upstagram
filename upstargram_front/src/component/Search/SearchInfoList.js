import {Card} from "react-bootstrap";
import FollowCard from "../Follow/FollowCard";

const SearchInfoList = (props) => {
    const {searchInfo, searchInfoList} = props;

    // 사용자 상세조회(Mypage)
    const userInfoDetail = (id) => {
        // TODO: navigate
        console.log(id);
    }

    // 해시태그 상세조회(Feed)
    const hashtagDetail = (hashtag) => {
        // TODO: navigate
        console.log(hashtag);
    }

    return (
        <Card>
            <div>
                {
                // User 조회
                searchInfo.searchDivisionCode === '1'
                &&
                (
                    searchInfoList.length === 0 
                    ?
                        <div>
                            조회된 내용이 없습니다.
                        </div>
                    :
                    searchInfoList.map((item, idx) => (
                        <div key={idx} className="m-2">
                            <FollowCard
                                followYn={'Y'}
                                memberInfo={item}
                                onclickEvent={() => userInfoDetail(item.id)}
                            />
                        </div>
                    ))
                )
                }

                {
                // Feed 조회
                searchInfo.searchDivisionCode === '2'
                &&
                (
                    searchInfoList.length === 0 
                    ?
                        <div>
                            조회된 내용이 없습니다.
                        </div>
                    :
                    searchInfoList.map((item, idx) => (
                        <div key={idx}>
                            <Card className="m-2" onClick={() => hashtagDetail(item.hashtag)}>
                                <div className="row">
                                    <div className="col-sm-2 align-self-center pointer text-center">
                                        <img alt="이미지" width={'30px;'} src="/images/hashtag.png"/>
                                    </div>
                                    <div className="col-sm-10 text-left align-self-center pointer">
                                        <h5>#{item.hashtag}</h5>
                                        <h6>게시글 {item.hashtagCnt} 건</h6>
                                    </div>
                                </div>
                            </Card>
                            
                        </div>
                    ))
                )
                }
            </div>
        </Card>
    )
}

export default SearchInfoList;