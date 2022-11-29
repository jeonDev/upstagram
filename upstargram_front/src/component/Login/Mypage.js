import React, {useState} from "react";
import { useNavigate } from "react-router-dom";
import { logout } from "../../api/LoginApi";

const Mypage = () => {
    /** Navigate */
    const navigate = useNavigate();

    const logoutClick = async () => {
        const result = await logout();
        if(result.code === 200) {
            localStorage.removeItem("Authorization");
            window.location.href = "/main";
        }
    }

    return (
        <div className="container mt-5">
            <div className="mb-4">
                <button className="btn btn-dark" onClick={logoutClick}>로그아웃</button>
            </div>
        </div>
    );
}
export default Mypage;