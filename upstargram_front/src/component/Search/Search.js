
import {useState} from "react";
import SearchDivision from "./SearchDivision";

const Search = () => {

    const [searchDivisionCode, setSearchDivisionCode] = useState('1');      // 1 : 사용자, 2 : 해시태그, 3 : ?

    // 값 변경
    const handleData = (value) => {
        setSearchDivisionCode(value);
    }

    return (
        <div className="container bg-light">

            <div className="btn-group d-flex w-100 p-2" role="group" aria-label="...">
                <button type="button" className={searchDivisionCode === '1' ? "btn btn-light w-100 active" : "btn btn-light w-100"} onClick={() => handleData('1')}>
                    사용자조회
                </button>
                <button type="button" className={searchDivisionCode === '2' ? "btn btn-light w-100 active" : "btn btn-light w-100"} onClick={() => handleData('2')}>
                    피드 조회
                </button>
            </div>

            {/* TODO: 버튼 클릭하면 구분 변경되게*/}
            <SearchDivision
                searchDivisionCode={searchDivisionCode}
            />
        </div>
    )
}

export default Search;