import {Card} from "react-bootstrap";
import { selectSearchList } from "../../api/SearchApi";

const SearchMenu = (props) => {
    const {searchInfo, setSearchInfo, searchInfoList, setSearchInfoList} = props;

    // 값 변경
    const handleData = (e) => {
        setSearchInfo({
            ...searchInfo,
            [e.target.name] : e.target.value
        });
    }

    // Enter 이벤트
    const onKeyEnter = async (e) => {
        if(e.key === 'Enter') {
            search();
        }
    }

    // 검색
    const search = async () => {

        const result = await selectSearchList(searchInfo.searchDivisionCode, searchInfo.searchValue);

        if(result.code === 200) {
            setSearchInfoList(result.data);
        } else {
            alert(result.message);
        }
    }

    return (
        <Card className="border-0">
            <div className="row">
                <div className="col-sm-9">
                    <input type="text" className="form-control" 
                           name="searchValue"
                           placeholder="검색어 입력"
                           value={searchInfo.searchValue}
                           onChange={handleData}
                           onKeyUp={onKeyEnter}
                    />
                </div>
                <div className="col-sm-3">
                    <button className="btn btn-outline-primary btn-block w-100"
                            onClick={search}
                    >
                        검색
                    </button>
                </div>
            </div>

        </Card>
    )
}

export default SearchMenu;