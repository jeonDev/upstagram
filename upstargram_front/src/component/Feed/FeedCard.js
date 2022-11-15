import React, { useEffect, useState } from "react";
import "../../assets/css/Main.css";

const FeedCard = (props) => {
    const {feed} = props;
    const [feedFile, setFeedFile] = useState([{
        "feedFileName": "cae7853d-a84b-4092-9aee-f841d238bce0_KakaoTalk_Video_20190928_1841_49_515.mp4",
        "feedExt": "video/mp4"
    }]);

    useEffect( () => {
        const feedFileNames = feed.feedFileNames.split(',');
        const feedFileExts = feed.feedExts.split(',');
        for(let i = 0; i <= feedFileNames.length; i++) {
            const newObj = {
                feedFileName : feedFileNames[i],
                feedExt : feedFileExts[i]
            };
//            setFeedFile([...feedFile, newObj]);
        }
        console.log(feedFile);
    }, []);

    return (
        <div className={"container bg-light mb-2 w-50"}>
            <div className="row p-2">
                <div className={"col-sm-2 text-left"}>
                    <img className="pointer" alt="이미지" width={'30px;'} src={'/images/mypage.jpg'}/>
                </div>
                <div className={"col-sm-8 text-left"}>
                    <div>
                        <span className="pointer p-2">{feed.nickname}</span>
                        <span className="pointer p-2">팔로우</span>
                    </div>
                </div>
                <div className={"col-sm-2 pointer"}>
                    ...
                </div>
            </div>

            <div className={"row bg-white m-auto"} style={style}>
                {
                    feedFile.map((item, idx) => {
                        if(item.feedExt.split('/')[0] === 'image'){
                            <img src={process.env.REACT_APP_SERVER_FILE_URL + item.feedFileName}/>
                        } else if (item.feedExt.split('/')[0] === 'video'){
                            <video autoplay>
                                <source src={process.env.REACT_APP_SERVER_FILE_URL + item.feedFileName} type={item.feedExt}/>
                            </video>
                        }
                    })
                }
            </div>

            <div className="d-flex justify-content-between">
                <div>
                    <span className="pointer p-1">
                        { true &&
                            <img className="pointer" alt="좋아요" width={'30px;'} src={'/images/feed_heart_n.png'}/>
                        }
                        { false &&
                            <img className="pointer" alt="좋아요 취소" width={'30px;'} src={'/images/feed_heart_y.png'}/>
                        }
                    </span>
                    <span className="pointer p-1">
                        <img className="pointer" alt="댓글" width={'30px;'} src={'/images/comment.png'}/>
                    </span>
                    <span className="pointer p-1">
                        <img className="pointer" alt="이미지" width={'30px;'} src={'/images/dm.png'}/>
                    </span>
                </div>
                <div>
                    <div>
                        <img className="pointer" alt="이미지" width={'30px;'} src={'/images/save_feed.png'}/>
                    </div>
                </div>
            </div>
            <div className={"row p-2"}>
                <span className="pointer text-left">{'좋아요 : ' + feed.feedHeartCnt + '개'}</span>
            </div>
            <div className={"row pointer p-2"}>
                <span className="pointer text-left">{'댓글 ' + feed.feedCommentCnt + '건 더 보기'}</span>
            </div>
        </div>
    );
}

export default FeedCard;

const style={
    width:'100%',
    height:'300px'
}