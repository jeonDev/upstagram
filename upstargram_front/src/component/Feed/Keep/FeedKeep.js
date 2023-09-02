import { useEffect, useState } from "react";
import { selectFeedList } from "../../../api/FeedApi";
import FeedCard from "../FeedCard";

const FeedKeep = () => {
    
    const [feedList, setFeedList] = useState([]);

    // Feed 조회
    const feedSearch = async () => {
        await selectFeedList({
            feedDivisionCode : '2'
        })
            .then((response) => {
                setFeedList(response.data);
            })
            .catch((error) => {
                console.log(error.response.data.message);
            });
    }

    useEffect(() => {
        feedSearch();
    }, [])

    return (
        <div>
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
        </div>
    )
}


export default FeedKeep;