import { useEffect, useState } from "react";
import { selectFeedList } from "../../../api/FeedApi";
import FeedCard from "../FeedCard";

const FeedKeep = () => {
    
    const [feedList, setFeedList] = useState([]);

    const searchFeedKeepList = async () => {
        const param = {feedKeepYn : 'Y', feedDivisionCode : '2'}
        const result = await selectFeedList(param);
        
        if(result.code === 200) {
            setFeedList(result.data);
        }
    }

    useEffect(() => {
        searchFeedKeepList();
    }, [])

    return (
        <div>
            <div>
                {
                    feedList.map((feed, idx) => (
                        <div key={idx}>
                            <FeedCard
                                feed={feed}
                            />
                        </div>
                    ))
                }
            </div>
        </div>
    )
}


export default FeedKeep;