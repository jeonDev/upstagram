import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import "../../assets/css/Main.css";
import {feedHeartSave, feedKeepSave} from "../../api/FeedApi";
import utils from "../../config/utils";

const FeedCard = (props) => {
    const {feed} = props;
    const [feedFile, setFeedFile] = useState([]);
    const [feedKeepYn, setFeedKeepYn] = useState( !utils.isNotEmpty(feed.feedKeepNo));
    const [feedHeartYn, setFeedHeartYn] = useState (!utils.isNotEmpty(feed.feedHeartNo));

    useEffect( () => {
        
        const feedFileNames = feed.feedFileNames.split(',');
        const feedFileExts = feed.feedExts.split(',');

        for(let i=0; i < feedFileNames.length; i++) {
            setFeedFile([
                ...feedFile,
                {
                    'feedFileName': feedFileNames[i],
                    'feedExt': feedFileExts[i]
                }
            ]);
        }
    }, []);

    // 피드 좋아요
    const saveFeedHeart = async () => {
        const result = await feedHeartSave(feed.feedNo);

        if(result.code === 200) {
            setFeedHeartYn(!feedHeartYn);
        }
    }
    // 피드 저장
    const saveFeedKeep = async () => {
        const result = await feedKeepSave(feed.feedNo);

        if(result.code === 200) {
            setFeedKeepYn(!feedKeepYn);
        }
    }

    return (
        <div className={"container bg-light p-2 mt-3"} style={feedCard}>
            {/* 사용자 정보 */}
            <Card variant="outlined" className="mt-2">
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
            </Card>

            {/* 이미지 및 동영상 */}
            <Card variant="outlined" className="mt-2">
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
            </Card>
            
            {/* 버튼 */}
            <div className="d-flex justify-content-between p-2">
                <div>
                    <span className="pointer p-1" onClick={saveFeedHeart}>
                        <img alt="좋아요" width={'30px;'} src={ feedHeartYn ? '/images/feed_heart_n.png' : '/images/feed_heart_y.png'}/>
                    </span>
                    <span className="pointer p-1">
                        <img alt="댓글" width={'30px;'} src={'/images/comment.png'}/>
                    </span>
                    <span className="pointer p-1">
                        <img alt="이미지" width={'30px;'} src={'/images/dm.png'}/>
                    </span>
                </div>
                <div>
                    <div className="pointer" onClick={saveFeedKeep}>
                        <img alt="이미지" width={'30px;'} src={ feedKeepYn ? '/images/save_feed_y.png' : '/images/save_feed_n.png'}/>
                    </div>
                </div>
            </div>

            {/* 좋아요, 게시글, 댓글 확인 */}
            <Card variant="outlined">
                <div className="row p-2">
                    <div className="pointer text-left">{'좋아요 : ' + feed.feedHeartCnt + '개'}</div>
                </div>
                <div className="row p-2">
                    <div className="text-left">
                        <span className="pointer">{feed.nickname} </span> 
                        <span className="pointer">{feed.title}</span>
                    </div>
                </div>
                <div className="row pointer p-2">
                    <div className="pointer text-left">{'댓글 ' + feed.feedCommentCnt + '건 더 보기'}</div>
                </div>
            </Card>
            
            {/* 댓글달기 */}
            <Card variant="outlined">
                <div className="row p-2">
                    <div className="col-sm-9">
                        <input type="text" className="form-control" placeholder="댓글달기"/>
                    </div>
                    <div className="col-sm-3">
                        <button className="btn btn-outline-primary w-100">게시</button>
                    </div>
                </div>
            </Card>
        </div>
    );
}

export default FeedCard;

const style={
    width:'100%',
    height:'300px'
}

const feedCard={
    width: '500px'
}