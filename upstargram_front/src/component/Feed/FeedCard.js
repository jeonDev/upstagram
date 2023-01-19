import React, { useEffect, useState } from "react";
import { Card } from "react-bootstrap";
import "../../assets/css/Main.css";
import {feedCommentRegister, feedHeartSave, feedKeepSave} from "../../api/FeedApi";
import utils from "../../config/utils";

const FeedCard = (props) => {
    const {feed} = props;
    const [onLoad, setOnLoad] = useState(false);
    const [feedData, setFeedData] = useState(feed);
    const [feedFile, setFeedFile] = useState([]);
    const [feedKeepYn, setFeedKeepYn] = useState( !utils.isNotEmpty(feed.feedKeepNo));
    const [curImageIdx, setCurImageIdx] = useState(0);

    // 댓글
    const [feedComment, setFeedComment] = useState('');

    useEffect( () => { 
        if(onLoad) return;
        setOnLoad(true);
        const feedFileNames = feedData.feedFileNames.split(',');
        const feedFileExts = feedData.feedExts.split(',');
        let data = [];

        for(let i=0; i < feedFileNames.length; i++) {
            data.push({
                'feedFileName': feedFileNames[i],
                'feedExt': feedFileExts[i]
            })
        }
        setFeedFile(data);
    }, [feedData]);

    // 피드 좋아요
    const saveFeedHeart = async () => {
        const result = await feedHeartSave(feedData.feedNo);

        if(result.code === 200) {
            setFeedData({
                ...feedData,
                feedHeartNo : result.data.feedHeartNo,
                feedHeartCnt : result.data.feedHeartCnt
            });
        }
    }
    
    // 피드 댓글 onchange
    const onChangeComment = (e) => {
        setFeedComment(e.target.value);
    }

    const handleOnKeyPress = (e) => {
        if(e.key === 'Enter') {
            saveFeedComment();
        }
    }

    // 피드 댓글 등록
    const saveFeedComment = async () => {
        const result = await feedCommentRegister({feedComment: feedComment, feedNo : feedData.feedNo});

        if(result.code === 200) {
            setFeedData({
                ...feedData,
                feedCommentCnt : result.data.feedCommentCnt,
            });
            setFeedComment('');
        }
    }

    // 피드 Keep 저장
    const saveFeedKeep = async () => {
        const result = await feedKeepSave(feedData.feedNo);

        if(result.code === 200) {
            setFeedKeepYn(!feedKeepYn);
        }
    }

    // 이미지 이동
    const moveImage = (divisionCode) => {
        const maxSize = feedFile.length - 1;
        
        // Prev
        if(divisionCode === 'prev') {
            if(0 === curImageIdx) setCurImageIdx(maxSize);
            else setCurImageIdx(curImageIdx - 1);
        // Next
        } else if(divisionCode === 'next') {
            if(maxSize === curImageIdx) setCurImageIdx(0);
            else setCurImageIdx(curImageIdx + 1);
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
                            <span className="pointer p-2">{feedData.nickname}</span>
                            <span className="pointer p-2">팔로우</span>
                        </div>
                    </div>
                    <div className={"col-sm-2 pointer"}>
                        ...
                    </div>
                </div>
            </Card>

            {/* 이미지 및 동영상 */}
            <Card variant="outlined" className="mt-2" style={{height: '400px'}}>
                <div
                    id="feed-image-list"
                    className="carousel slide carousel-fade card-body p-0 h-100"
                    data-bs-touch="false"
                    data-bs-interval="false"
                    // style={style}
                    >
                        <div className="carousel-inner h-100">
                        {
                            feedFile.map((item, idx) => (
                                <div key={idx} className={curImageIdx === idx ? 'carousel-item h-100 active' : 'carousel-item h-100'}>
                                    {
                                    item.feedExt.split('/')[0] === 'image'
                                    ?
                                         (<img className="d-block w-100 h-100" src={process.env.REACT_APP_SERVER_FILE_URL + item.feedFileName}/>)
                                    :
                                    item.feedExt.split('/')[0] === 'video'
                                    ?
                                         (
                                         <video className="d-block w-100 h-100" loop>
                                            <source src={process.env.REACT_APP_SERVER_FILE_URL + item.feedFileName} type={item.feedExt}/>
                                        </video>
                                         )
                                    :
                                         (<div className="d-block w-100 h-100"></div>)
                                    }
                                </div>
                            ))
                        }
                        </div>
                        <button
                            className="carousel-control-prev"
                            type="button"
                            onClick={ (divisionCode)=>{moveImage('prev')}}
                        >
                            <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span className="visually-hidden">Previous</span>
                        </button>
                        <button
                            className="carousel-control-next"
                            type="button"
                            onClick={ (divisionCode)=>{moveImage('next')}}
                        >
                            <span className="carousel-control-next-icon" aria-hidden="true"></span>
                            <span className="visually-hidden">Next</span>
                        </button>
                </div>
            </Card>
            
            {/* 버튼 */}
            <div className="d-flex justify-content-between p-2">
                <div>
                    <span className="pointer p-1" onClick={saveFeedHeart}>
                        <img alt="좋아요" width={'30px;'} src={ utils.isNotEmpty(feedData.feedHeartNo) ? '/images/feed_heart_n.png' : '/images/feed_heart_y.png'}/>
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
                    <div className="pointer text-left">{'좋아요 : ' + feedData.feedHeartCnt + '개'}</div>
                </div>
                <div className="row p-2">
                    <div className="text-left">
                        <span className="pointer">{feedData.nickname} </span> 
                        <span className="pointer">{feedData.title}</span>
                    </div>
                </div>
                <div className="row pointer p-2">
                    <div className="pointer text-left">{'댓글 ' + feedData.feedCommentCnt + '건 더 보기'}</div>
                </div>
            </Card>
            
            {/* 댓글달기 */}
            <Card variant="outlined">
                <div className="row p-2">
                    <div className="col-sm-9">
                        <input 
                            type="text" 
                            className="form-control" 
                            value={feedComment}
                            onChange={onChangeComment}
                            onKeyPress={handleOnKeyPress} // Enter 입력 이벤트 함수
                            placeholder="댓글달기"
                            />
                    </div>
                    <div className="col-sm-3">
                        <button className="btn btn-outline-primary w-100" onClick={saveFeedComment}>게시</button>
                    </div>
                </div>
            </Card>
        </div>
    );
}

export default FeedCard;

const feedCard={
    width: '500px'
}