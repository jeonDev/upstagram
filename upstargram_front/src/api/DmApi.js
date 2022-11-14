import httpRequest from "../config/httpRequest";

// DmChatRoom 조회
export async function selectDmChatRoomList () {
    return await httpRequest.get('/user/dm/room/list')
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}

// DmChatRoom 생성
export async function createDmChatRoom (id) {
    return await httpRequest.post('/user/dm/room/create/' + id)
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}

// DmChat 조회
export async function selectDmChatList (dmChatRoom) {
    return await httpRequest.get('/user/dm/list/' + dmChatRoom)
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}