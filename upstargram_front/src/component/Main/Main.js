import {selectFeedList} from "../../api/FeedApi";
import {join} from "../../api/LoginApi";
import React, {useEffect, useState} from "react";
import {selectStoryList} from "../../api/StoryApi";
import FeedCard from "../Feed/FeedCard";

const Main = () => {
    const [feedList, setFeedList] = useState([]);
    const [storyList, setStoryList] = useState([]);

    useEffect(() => {
        feedSearch();
        storySearch();

    }, [])

    // Feed 조회
    const feedSearch = async () => {
        await selectFeedList()
            .then((response) => {
                setFeedList(response.data);
            })
            .catch((error) => {
                alert(error.response.data.message);
            });
    }

    // Story 조회
    const storySearch = async () => {
        await selectStoryList()
            .then((response) => {
                setStoryList(response.data);
            })
            .catch((error) => {
                alert(error.response.data.message);
            });
    }

    return (
        <div>
            <div>
                {feedList.map(feed => (
                    FeedCard({feed})
                ))}
                {storyList.map(story => (
                    <>
                        <div>닉네임 : {story.nickname}</div>
                    </>
                ))}
            </div>
        </div>
    );
}

export default Main;