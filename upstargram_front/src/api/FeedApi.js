import httpRequest from "../config/httpRequest";

// Feed 조회
export async function selectFeedList () {
    return await httpRequest.get('user/feed/list')
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}