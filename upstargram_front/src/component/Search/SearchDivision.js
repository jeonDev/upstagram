import SearchMenu from "./SearchMenu";
import SearchInfoList from "./SearchInfoList";
import {useEffect, useState} from "react";

const SearchDivision = (props) => {

    const {searchDivisionCode} = props;

    const [searchInfo, setSearchInfo] = useState({      // 검색어
        searchDivisionCode : searchDivisionCode,        // 1 : 사용자, 2 : 해시태그, 3 : ?
        searchValue : ''
    });

    const [searchInfoList, setSearchInfoList] = useState([]);     // 검색결과

    // 사용자 상세조회(Mypage)
    const userInfoDetail = (item) => {
        // TODO: navigate
        console.log(item.id);
    }

    // 해시태그 상세조회(Feed)
    const hashtagDetail = (item) => {
        // TODO: navigate
        console.log(item.hashtag);
    }

    useEffect(() => {
        setSearchInfo({
            ...searchInfo,
            searchDivisionCode : searchDivisionCode
        })
        setSearchInfoList([]);
    }, [searchDivisionCode]);

    return (
        <div className="container bg-light">
            <div className="p-3">
                <SearchMenu
                    searchInfo={searchInfo}
                    setSearchInfo={setSearchInfo}
                    searchInfoList={searchInfoList}
                    setSearchInfoList={setSearchInfoList}
                />
            </div>
            <div>
                <SearchInfoList
                    searchInfo={searchInfo}
                    searchInfoList={searchInfoList}
                    followYn={'Y'}
                    userDetailClick={(item) => userInfoDetail(item)}
                    hashtagDetailClick={(item) => hashtagDetail(item)}
                />
            </div>
        </div>
    )
}

export default SearchDivision;