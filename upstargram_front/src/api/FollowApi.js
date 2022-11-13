import httpRequest from "../config/httpRequest";

// Follow 조회
export async function selectFollowList () {
    return await httpRequest.get('/user/follow/list')
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}