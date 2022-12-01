import { useEffect, useState } from "react";
import { sendDmChat } from "../../api/DmApi";

const DmMessage = (props) => {
    const {dmChatRoom, dmChatList, setDmChatList, receiveId} = props; // 방 No / DM 목록

    const [data, setData] = useState({
        message : ''
    });
    const [sendMessage, setSendMessage] = useState(false);

    // 값 변경
    const handleData = (e) => {
        setData({
            ...data,
            [e.target.name] : e.target.value
        });
    }

    // Enter 이벤트
    const onKeyEnter = async (e) => {
        if(e.key == 'Enter') {
            onSendDm();
        }
    }

    // 전송버튼 클릭
    const onSendDm = async () => {
        setSendMessage(!sendMessage);
    }

    // DM 전송
    const sendDmMessage = async () => {
        const result = await sendDmChat(data.message, dmChatRoom, receiveId)
        
        if(result.code === 200) {
            setDmChatList([
                ...dmChatList,
                {
                    dmChatNo: result.data.dmChatNo,
                    dmChatRoomNo: result.data.dmChatRoomNo,
                    message: result.data.message,
                    sendId: result.data.sendId,
                    sendName: result.data.sendName,
                    sendNickname: result.data.sendNickname,
                    sendOauthNo: result.data.sendOauthNo,
                    sendSex: result.data.sendSex,
                    sendTel: result.data.sendTel,
                    viewYn: result.data.viewYn
                }
            ]);

            setData({
                message: '' // message 초기화
            });
        }
        ;
    }

    useEffect(() => {
        if(!data.message) return;
        sendDmMessage();
    }, [sendMessage])

    return (
        <div className="row">
            <div className="col-sm-9">
                <input type="text" className="form-control" name="message" id="message" placeholder="메시지를 입력해주세요." 
                    value={data.message}
                    onChange={handleData}
                    onKeyPress={onKeyEnter}
                />
            </div>
            <div className="col-sm-3">
                <button className="btn btn-outline-primary btn-block w-100" onClick={onSendDm}>전송</button>
            </div>
        </div>
    )
}

export default DmMessage;