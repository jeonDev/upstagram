import httpRequest from "../config/httpRequest";

// Feed 조회
export async function selectFeedList (param) {
    return await httpRequest.get('user/feed/list', {
        params : param
    })
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}

// Feed 보관
export async function feedKeepSave(feedNo) {
    return await httpRequest.post('user/feed/keep/save', {
        feedNo : feedNo
    })
        .then( (response) => {
            return response.data
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}

// Feed 좋아요
export async function feedHeartSave(feedNo) {
    return await httpRequest.post('user/feed/heart/save', {
        feedNo : feedNo
    })
    .then( (response) => {
        return response.data
    })
    .catch( (error) => {
        return Promise.reject(error);
    })
}

// Feed 등록
export async function feedRegister(data) {
    return await httpRequest.post('user/feed/regist', data,
    {
        headers : {"Content-Type" : "multipart/form-data"}
    })
    .then( (response) => {
        return response.data;
    })
    .catch( (error) => {
        return error.response.data;
    })
}