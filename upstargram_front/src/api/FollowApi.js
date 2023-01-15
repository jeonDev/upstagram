import httpRequest from "../config/httpRequest";

// Follow 조회
export async function selectFollowList () {
    return await httpRequest.get('user/follow/list')
    .then( (response) => {
        return response.data;
    })
    .catch( (error) => {
        return Promise.reject(error);
    })
}

// Follow 요청
export async function insertFollowUser (followId) {
    return await httpRequest.post('user/follow/add', {
        followId: followId
    })
    .then( (response) => {
        return response.data;
    })
    .catch( (error) => {
        return error.response;
    })
}

// Follow 취소
export async function deleteFollowUser (followNo) {
    return await httpRequest.post('user/follow/delete', {
        followNo: followNo
    })
    .then( (response) => {
        return response.data;
    })
    .catch( (error) => {
        return error.response;
    })
}