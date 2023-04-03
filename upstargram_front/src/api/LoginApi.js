import httpRequest from "../config/httpRequest";

// 로그인
export async function login (data) {
    return await httpRequest.post('login', data)
    .then( (response) => {
        return response.data;
    })
    .catch( (error) => {
        return error.response;
    })
}

// 로그아웃
export async function logout () {
    return await httpRequest.post('user/logout')
    .then( (response) => {
        return response.data;
    })
    .catch( (error) => {
        return error.response;
    })
}

// 회원가입
export async function join (data) {
    return await httpRequest.post('join', data)
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return error.response;
        })
}

// Refresh Token 요청
export async function tokenReIssueRequest(){
    return await httpRequest.post('token/re/issue')
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return error.response;
        })
}

// 회원정보 조회 및 Token 유효 확인
export async function selectUserInfo() {
    return await httpRequest.get('user/info/get')
    .then( (response) => {
        return response.data;
    })
    .catch( (error) => {
        return error.response;
    })
}