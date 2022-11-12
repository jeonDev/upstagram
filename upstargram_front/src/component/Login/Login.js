import React, {useState} from "react";
import {login} from "../../api/LoginApi";
import {Link, useNavigate} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';

const Login = (props) => {
    /** Navigate */
    const navigate = useNavigate();

    const { close } = props;
    const [data, setData] = useState({
        id : '',
        password : ''
    });

    // 값 변경
    const handleData = (e) => {
        setData({
            ...data,
            [e.target.name] : e.target.value
        });
    }

    const onKeyPress = (e) => {
        if(e.key === 'Enter') loginApply();
    }

    // 로그인
    const loginApply = async () => {
        await login(data)
            .then((response) => {
                if(response.code === 200) {
                    const token = response.data.accessToken;
                    localStorage.setItem("Authorization", token);
                    // alert(response.message);
                    navigate("/main");
                    close();
                }
            })
            .catch((error) => {
                alert(error.response.data.message);
            });
    }

    // 구글 로그인
    const googleLogin = () => {
        window.location.href = process.env.REACT_APP_GOOGLE_URI + "?"
            + "client_id=" + process.env.REACT_APP_GOOGLE_CLIENT_ID + "&"
            + "redirect_uri=" + process.env.REACT_APP_GOOGLE_CALLBACK_URI + "&"
            + "response_type=token&"
            + "scope=https://www.googleapis.com/auth/userinfo.email  https://www.googleapis.com/auth/userinfo.profile";
    }

    return (
        <div className="container">
            <div className="row m-1">
                <input type='text' className="form-control" name='id' value={data.id} placeholder="아이디(ID) 입력"
                    onChange={handleData} onKeyPress={onKeyPress} />
                <input type="password" className="form-control" name="password" value={data.password} placeholder="패스워드(PASSWORD) 입력"
                    onChange={handleData} onKeyPress={onKeyPress}/>
            </div>
            <div className="row m-1">
                <input type="button" className="btn btn-primary" id="loginBtn" value="login" onClick={loginApply}/>
            </div>
            <div className="row m-1">
                <Link className="btn btn-dark" to='/join' onClick={close}>회원가입</Link>
            </div>
            <div className="row m-1">
                <button onClick={() => googleLogin()}>
                    <img className="" alt="로그인" width={'30px;'} src={'/images/google.png'}/>Google Login
                </button>
            </div>
        </div>
    );
}
export default Login;