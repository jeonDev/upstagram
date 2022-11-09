import httpRequest from "../config/httpRequest";

// Feed 조회
export async function selectStoryList () {
    return await httpRequest.get('/user/story/list')
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}