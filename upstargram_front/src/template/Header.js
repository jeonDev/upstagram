import React, {useState} from "react";
import {Link} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';
import Login from "../component/Login/Login";
import Modal from "./Modal";

const Header = () => {
    const [loginModalOpen, setloginModalOpen] = useState(false);

    // Login Modal open & close
    const loginModal = () => {
        setloginModalOpen(true);
    };
    const closeLoginModal = () => {
        setloginModalOpen(false);
    };

    /** Navigate */
    //const navigate = useNavigate();


    return (
        <>
            <nav className="navbar bg-light d-flex justify-content-between mb-2">
                <div>
                    <Link className="navbar-brand p-2" to='/main'>
                        <img className="" alt="Upstagram" width={'30px;'} src={'/images/main.png'}/>
                    </Link>
                </div>
                <div>
                    <span className="p-3">
                        <Link to='/main'>
                            <img className="" alt="메인" width={'30px;'} src={'/images/home.png'}/>
                        </Link>
                    </span>
                    <span className="p-3" onClick={loginModal}>
                        <img className="" alt="로그인" width={'30px;'} src={'/images/mypage.jpg'}/>
                    </span>
                    {/* <span className="p-3">
                        <img className="" alt="마이페이지" width={'30px;'} src={'/images/mypage.jpg'}/>
                    </span> */}
                    <span className="p-3">
                        <Link to='/search'>
                            <img className="" alt="검색" width={'30px;'} src={'/images/search.png'}/>
                        </Link>
                    </span>
                    <span className="p-3">
                        <Link to='/dm/list'>
                            <img className="" alt="DM" width={'30px;'} src={'/images/dm.png'}/>
                        </Link>
                    </span>
                    <span className="p-3">
                        <Link to='/feed/keep'>
                            <img className="" alt="보관" width={'30px;'} src={'/images/save_feed.png'}/>
                        </Link>
                    </span>
                    <span className="p-3">
                        <Link to='/push'>
                            <img className="" alt="알림" width={'30px;'} src={'/images/heart.png'}/>
                        </Link>
                    </span>
                </div>
            </nav>
            <div>
                <Modal open={loginModalOpen} close={closeLoginModal} header="로그인">
                    <Login close={closeLoginModal}/>
                </Modal>
            </div>
        </>
    );
}
export default Header;