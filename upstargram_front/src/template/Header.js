import React, {useState} from "react";
import {Link} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';
import { useSelector } from "react-redux";
import utils from "../config/utils";

const Header = () => {
    const id = useSelector(state => state.id);

    return (
        <>
        {
            !utils.isNotEmpty(id) && 
            <nav className="navbar bg-light d-flex justify-content-between mb-2">
                <div>
                    <Link className="navbar-brand p-2" to='/main'>
                        <img className="" alt="Upstagram" width={'30px;'} src={'/images/main.png'}/>
                    </Link>
                </div>
                <div>
                    <span className="p-3">
                        <Link to='/feed/register'>
                            <img className="" alt="등록" width={'30px;'} src={'/images/regist.png'}/>
                        </Link>
                    </span>
                    <span className="p-3">
                        <Link to='/main'>
                            <img className="" alt="메인" width={'30px;'} src={'/images/home.png'}/>
                        </Link>
                    </span>
                    <span className="p-3">
                        <Link to='/mypage'>
                            <img className="" alt="마이페이지" width={'30px;'} src={'/images/mypage.jpg'}/>
                        </Link>
                    </span>
                    <span className="p-3">
                        <Link to='/search'>
                            <img className="" alt="검색" width={'30px;'} src={'/images/search.png'}/>
                        </Link>
                    </span>
                    <span className="p-3">
                        <Link to='/dm'>
                            <img className="" alt="DM" width={'30px;'} src={'/images/dm.png'}/>
                        </Link>
                    </span>
                    <span className="p-3">
                        <Link to='/feed/keep'>
                            <img className="" alt="보관" width={'30px;'} src={'/images/save_feed_y.png'}/>
                        </Link>
                    </span>
                    <span className="p-3">
                        <Link to='/push'>
                            <img className="" alt="알림" width={'30px;'} src={'/images/heart.png'}/>
                        </Link>
                    </span>
                </div>
            </nav>
            }
        </>
    );
}
export default Header;