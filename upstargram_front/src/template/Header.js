import React, {useState} from "react";
import {Link} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.css';

const Header = () => {
    const [loginModalOpen, setLoginModalOpen] = useState(false);

    // 모달창 노출
    const loginModal = () => {
        setLoginModalOpen(true);
    };

    /** Navigate */
    //const navigate = useNavigate();


    return (
        <nav className="navbar bg-light d-flex justify-content-between mb-2">
            <div>
                <Link className="navbar-brand p-2" to='/main'>Upstagram</Link>
            </div>
            <div>
                <span className="p-3" onClick={loginModal}>로그인</span>
                <span className="p-3">마이페이지</span>
                <span className="p-3">검색</span>
                <span className="p-3">DM</span>
                <span className="p-3">Story</span>
                <span className="p-3">알림</span>
                <image className="rounded" alt="마이페이지" src="/images/mypage.jpg"/>
            </div>
        </nav>
    );
}
export default Header;