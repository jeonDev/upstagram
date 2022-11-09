import React, {useState} from "react";
import {login} from "../../api/LoginApi";
import {Link, useNavigate} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';

const Login = () => {
    /** Navigate */
    const navigate = useNavigate();

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
                }
            })
            .catch((error) => {
                alert(error.response.data.message);
            });
    }

    // 구글 로그인
    const googleLogin = () => {
        window.location.href = "https://accounts.google.com/o/oauth2/auth?"
            + "client_id=826917971572-k2repj07adcidd5d4cdgm3cic7f396je.apps.googleusercontent.com&"
            + "redirect_uri=http://localhost:8098/login/callback/&"
            + "response_type=token&"
            + "scope=https://www.googleapis.com/auth/userinfo.email  https://www.googleapis.com/auth/userinfo.profile";
    }

    return (
        <div class="container">
            <div class="row">
                <input type='text' class="form-control" name='id' value={data.id} onChange={handleData} onKeyPress={onKeyPress} />
                <input type="password" class="form-control" name="password" value={data.password} onChange={handleData} onKeyPress={onKeyPress}/>
                <input type="button" class="btn btn-primary" id="loginBtn" value="login" onClick={loginApply}/>
            </div>
            <div class="row">
                <Link class="btn btn-dark" to='/join'>회원가입</Link>
            </div>
            <div class="row">
                <input type="button" class="btn btn-default" id="googleLoginBtn" value="googleLogin" onClick={() => googleLogin()}/>
            </div>
        </div>
    );
}
export default Login;