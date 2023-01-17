import {Card} from "react-bootstrap";
import FollowCard from "../Follow/FollowCard";

const SearchInfoList = (props) => {
    const {searchInfo           // 검색조건
        , searchInfoList        // 결과값
        , followYn              // 팔로우버튼 활성화 유무
        , userDetailClick       // 사용자조회 클릭 이벤트
        , hashtagDetailClick    // 해시태그조회 클릭 이벤트
        } = props;
    
    return (
        <Card>
            <div>
                {
                // User 조회
                (searchInfo.searchDivisionCode === '1'
                || searchInfo.searchDivisionCode === '3'
                )
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
                                followYn={followYn}
                                memberInfo={item}
                                onclickEvent={() => userDetailClick(item)}
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
                            <Card className="m-2" onClick={() => hashtagDetailClick(item)}>
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