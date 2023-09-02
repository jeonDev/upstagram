import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import { logout } from "../../api/LoginApi";
import FeedCard from "../Feed/FeedCard";
import { selectFeedList } from "../../api/FeedApi";

const Mypage = () => {
    /** Navigate */
    const navigate = useNavigate();

    const [feedList, setFeedList] = useState([]);

    // Feed 조회
    const feedSearch = async () => {
        await selectFeedList({
            feedDivisionCode : '3'
        })
            .then((response) => {
                setFeedList(response.data);
            })
            .catch((error) => {
                console.log(error.response.data.message);
            });
    }

    const logoutClick = async () => {
        const result = await logout();
        if(result.code === 200) {
            localStorage.removeItem("Authorization");
            window.location.href = "/main";
        }
    }

    useEffect(() => {
        feedSearch();
    }, [])
    return (
        <div className="container mt-5">
            <div>
            {
                feedList.length > 0 
                &&
                feedList.map((feed, idx) => (
                    <div key={idx}>
                        <FeedCard
                            feed={feed}
                        />
                    </div>
            ))}
            </div>
            <div className="mb-4">
                <button className="btn btn-dark" onClick={logoutClick}>로그아웃</button>
            </div>
        </div>
    );
}
export default Mypage;