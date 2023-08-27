import {selectFeedList} from "../../api/FeedApi";
import React, {useEffect, useState} from "react";
import {selectStoryList} from "../../api/StoryApi";
import FeedCard from "../Feed/FeedCard";

const Main = () => {
    const [feedList, setFeedList] = useState([]);
    const [storyList, setStoryList] = useState([]);

    useEffect(() => {
        feedSearch();
    }, [])

    // Feed 조회
    const feedSearch = async () => {
        await selectFeedList({
            feedDivisionCode : '1'
        })
            .then((response) => {
                setFeedList(response.data);
                storySearch();
            })
            .catch((error) => {
                console.log(error.response.data.message);
            });
    }

    // Story 조회
    const storySearch = async () => {
        await selectStoryList()
            .then((response) => {
                setStoryList(response.data);
            })
            .catch((error) => {
                console.log(error.response.data.message);
            });
    }

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
                {
                storyList.length > 0
                    &&
                storyList.map(story => (
                    <div>닉네임 : {story.nickname}</div>
                ))}
            </div>
        </div>
    );
}

export default Main;