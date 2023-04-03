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
            return error.response;
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
            return error.response;
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
        return error.response;
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

// Feed 댓글 등록
export async function feedCommentRegister(data) {
    return await httpRequest.post('user/feed/comment/regist', data)
    .then( (response) => {
        return response.data;
    })
    .catch( (error) => {
        return error.response.data;
    })
}