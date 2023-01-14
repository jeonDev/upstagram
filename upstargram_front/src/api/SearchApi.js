import httpRequest from "../config/httpRequest";

// Search 조회
export async function selectSearchList (searchDivisionCode, searchValue) {
    return await httpRequest.get('user/search',{
        params : {
            searchDivisionCode : searchDivisionCode,
            searchValue : searchValue
        }
    })
    .then( (response) => {
        return response.data;
    })
    .catch( (error) => {
        return error.response;
    })
}