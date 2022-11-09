import React, {useState, useEffect} from "react";
import {join} from "../../api/LoginApi";

const Join = () => {
    const [data, setData] = useState({
        id : '',
        password : '',
        name : '',
        nickname : '',
        sex : '',
        tel : '',
        useYn : '',
        role : ''
    });

    const handleData = (e) => {
        setData({
            ...data,
            [e.target.name] : e.target.value
        });
    }

    const joinApply = async () => {
        await join(data)
            .then((response) => {
                if(response.code == "200") {
                    alert(response.message);
                }
            })
            .catch((error) => {
                alert(error.response.data.message);
            });
    }

    return (
        <div>
            <div>
                <input type='text' name='id' value={data.id} onChange={handleData} />
            </div>
                <input type="password" name="password" value={data.password} onChange={handleData}/>
            <div>
                <input type='text' name='name' value={data.name} onChange={handleData} />
            </div>
            <div>
                <input type='text' name='nickname' value={data.nickname} onChange={handleData} />
            </div>
            <div>
                <select name='sex' value={data.sex} onChange={handleData}>
                    <option value='M'>남자</option>
                    <option value='W'>여자</option>
                </select>
            </div>
            <div>
                <input type='text' name='tel' value={data.tel} onChange={handleData} />
            </div>
            <div>
                <input type="button" id="joinBtn" value="join" onClick={joinApply}/>
            </div>
        </div>
    );
}
export default Join;