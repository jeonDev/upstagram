import {Card} from "react-bootstrap";
import FollowCard from "../Follow/FollowCard";

const SearchInfoList = (props) => {
    const {searchInfo, searchInfoList} = props;

    const userInfoDetail = (id) => {

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
                        <FollowCard
                            key={idx}
                            followYn={'Y'}
                            id={item.id}
                            name={item.name}
                            nickname={item.nickname}
                            profileImage={item.profileImage}
                            onclickEvent={() => userInfoDetail(item.id)}
                        />
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
                            {item.feedNo}
                        </div>
                    ))
                )
                }
            </div>
        </Card>
    )
}

export default SearchInfoList;