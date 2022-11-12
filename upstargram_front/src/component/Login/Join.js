import React, {useState} from "react";
import { useNavigate } from "react-router-dom";
import {join} from "../../api/LoginApi";

const Join = () => {
    /** Navigate */
    const navigate = useNavigate();

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
                if(response.code === 200) {
                    alert(response.message);
                    navigate("/main");
                }
            })
            .catch((error) => {
                alert(error.response.data.message);
            });
    }

    return (
        <div className="container mt-5">
            <div className="mb-4">
                <h2>회원가입</h2>
            </div>
            <div className="row m-2">
                <div className="col-sm-3 text-lg-start">
                    아이디(ID)
                </div>
                <div className="col-sm-9">
                    <input type='text' className="form-control" name='id' value={data.id} placeholder="아이디(ID) 입력" onChange={handleData} />
                </div>
            </div>
            <div className="row m-2">
                <div className="col-sm-3 text-lg-start">
                    패스워드(PASSWORD)
                </div>
                <div className="col-sm-9">
                    <input type="password" className="form-control" name="password" value={data.password} placeholder="패스워드(PASSWORD) 입력" onChange={handleData}/>
                </div>
            </div>
            <div className="row m-2">
                <div className="col-sm-3 text-lg-start">
                    이름(NAME)
                </div>
                <div className="col-sm-9">
                    <input type='text' className="form-control" name='name' value={data.name} placeholder="이름(NAME) 입력" onChange={handleData} />
                </div>
            </div>
            <div className="row m-2">
                <div className="col-sm-3 text-lg-start">
                    닉네임(NICKNAME)
                </div>
                <div className="col-sm-9">
                    <input type='text' className="form-control" name='nickname' value={data.nickname} placeholder="닉네임(NICKNAME) 입력" onChange={handleData} />
                </div>
            </div>
            <div className="row m-2">
                <div className="col-sm-3 text-lg-start">
                    성별(SEX)
                </div>
                <div className="col-sm-9">
                    <select name='sex' className="form-control" value={data.sex} onChange={handleData}>
                        <option value='' selected disabled>성별(SEX) 선택</option>
                        <option value='M'>남자</option>
                        <option value='W'>여자</option>
                    </select>
                </div>
            </div>
            <div className="row m-2">
                <div className="col-sm-3 text-lg-start">
                    전화번호(TEL)
                </div>
                <div className="col-sm-9">
                    <input type='text' className="form-control" name='tel' value={data.tel} placeholder="전화번호(TEL) 입력(-제외)" onChange={handleData} />
                </div>
            </div>
            <div className="row">
                <button type="button" className="btn btn-outline-success" onClick={joinApply}>회원가입</button>
            </div>
        </div>
    );
}
export default Join;