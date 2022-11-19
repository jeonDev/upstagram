import httpRequest from "../config/httpRequest";

// DmChatRoom 조회
export async function selectDmChatRoomList () {
    return await httpRequest.get('user/dm/room/list')
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}

// DmChatRoom 생성
export async function createDmChatRoom (id) {
    return await httpRequest.post('user/dm/room/create/' + id)
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}

// Dm 전송 (Data : message / dmChatRoomNo)
export async function sendDmChat(message, dmChatRoomNo) {
    return await httpRequest.post("user/dm/send", {
        message : message,
        dmChatRoomNo : dmChatRoomNo
    })
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            return Promise.reject(error);
        })
}

// DmChat 조회
export async function selectDmChatList (dmChatRoom) {
    return await httpRequest.get('user/dm/list/' + dmChatRoom)
        .then( (response) => {
            return response.data;
        })
        .catch( (error) => {
            return Promise.reject(error);
        })
}