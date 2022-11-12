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
                    <Link className="navbar-brand p-2" to='/main'>Upstagram</Link>
                </div>
                <div>
                    <span className="p-3" onClick={loginModal}>
                        <img className="" src={'/images/mypage.png'}/>
                    </span>
                    <span className="p-3">마이페이지</span>
                    <span className="p-3">검색</span>
                    <span className="p-3">DM</span>
                    <span className="p-3">Story</span>
                    <span className="p-3">알림</span>
                    <image className="rounded" alt="마이페이지" src="/images/mypage.jpg"/>
                </div>
            </nav>
            <div>
                <Modal open={loginModalOpen} close={closeLoginModal} header="로그인">
                    <Login/>
                </Modal>
            </div>
        </>
    );
}
export default Header;