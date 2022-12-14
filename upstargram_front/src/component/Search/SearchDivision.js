import SearchMenu from "./SearchMenu";
import SearchInfoList from "./SearchInfoList";
import {useEffect, useState} from "react";

const Search = (props) => {

    const {searchDivisionCode} = props;

    const [searchInfo, setSearchInfo] = useState({      // 검색어
        searchDivisionCode : searchDivisionCode,        // 1 : 사용자, 2 : 해시태그, 3 : ?
        searchValue : ''
    });

    const [searchInfoList, setSearchInfoList] = useState([]);     // 검색결과

    // 정보 조회
    const selectSearchInfo = async () => {

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
                    setSearchInfoList={setSearchInfoList}
                />
            </div>
            <div>
                <SearchInfoList
                    searchInfoList={searchInfoList}
                />
            </div>
        </div>
    )
}

export default Search;