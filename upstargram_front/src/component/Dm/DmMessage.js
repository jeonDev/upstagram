import { useEffect, useState } from "react";
import { sendDmChat } from "../../api/DmApi";

const DmMessage = (props) => {
    const {dmChatRoom, dmChatList, setDmChatList} = props; // 방 No / DM 목록

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

    const sendDmMessage = async () => {
        
        const result = await sendDmChat(data.message, dmChatRoom)
        .then((response) => {
            // TODO: List에 추가
        })
        .catch((error) => {
            console.log(error);
        })
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
                    onChange={handleData}/>
            </div>
            <div className="col-sm-3">
                <button className="btn btn-outline-primary btn-block w-100" onClick={() => setSendMessage(!sendMessage)}>전송</button>
            </div>
        </div>
    )
}

export default DmMessage;