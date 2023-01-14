import React, {useEffect, useRef, useState} from "react";
import { Card } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { feedRegister } from "../../api/FeedApi";
import "../../assets/css/Main.css";


const FeedRegister = () => {

    const navigate = useNavigate();

    const fileInput = useRef("");
    const [feed, setFeed] = useState({
       title    : '',
       hashtag  : '',
       useYn    : 'Y',
       tagId    : []
    });
    const [imageFile, setImageFile] = useState([]);
    const [curImageIdx, setCurImageIdx] = useState(0);

    // 값 변경
    const handleData = (e) => {
        setFeed({
            ...feed,
            [e.target.name] : e.target.value
        });
    }

    // 이미지 등록
    const onImageData = (e) => {
        const fileList = e.target.files;
        const fileSize = fileList?.length;
        let data = [];

        for(let i = 0; i < fileSize; i++) {
            const currentImageUrl = URL.createObjectURL(fileList[i]);

            data.push({
                file : fileList[i],
                viewImage : currentImageUrl,
                type : fileList[i].type.slice(0, 5)
            })
        }
        setImageFile(data);
    }

    // 피드 등록
    const registerFeed = async () => {

        if(imageFile.length === 0) {
            alert("이미지를 등록하세요.");
            return;
        }
        if(feed.title.length === 0) {
            alert("게시글을 입력하세요.");
            return;
        }

        let formData = new FormData();

        const hashtag = extraHashtags(feed.title);
        setFeed({
            ...feed,
            hashtag : hashtag
        });

        for(let i = 0; i < imageFile.length; i++) {
            formData.append('files', imageFile[i].file);
        }
        formData.append('pvo', new Blob([JSON.stringify(feed)], {type: "application/json"}));

        const result = await feedRegister(formData);
        if(result.code === 200) {
            alert(result.message);
            navigate('/main');
        } else {
            console.log(result);
            alert(result.message);
        }
    }

    // Hashtag 추출
    const extraHashtags = (title) => {
        const hashtags = title.split("#");
        const hashtag = [];

        for(let i = 1; i < hashtags.length; i++) {
            
            const hashtagString = hashtags[i];
            const firstChar = hashtagString.charAt(0);
            if(firstChar === " " || hashtagString === "") continue;
            
            const size = hashtagString.indexOf(" ")
            const str = hashtagString.slice(0, size === -1 ? hashtagString.length : size);

            hashtag.push(str);
        }

        return hashtag;
    }

    // 이미지 이동
    const moveImage = (divisionCode) => {
        const maxSize = imageFile.length - 1;
        
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
        <div className={"container bg-light p-2 mt-3"}>
            <div>
                <div className="m-3">
                    <h2>게시글 등록</h2>
                </div>
                <hr/>

                {/* 이미지 */}
                <Card style={{height: '400px'}}>
                    {
                        imageFile.length === 0 
                        &&
                        <div className="w-100 h-100 bg-white card-body d-flex justify-content-center align-items-center" onClick={() => fileInput.current.click()}>
                            <h2>
                                이미지를 등록하세요.
                            </h2>
                        </div>
                    }
                    {
                        imageFile.length > 0
                        &&
                        <div
                            id="feed-image-list"
                            className="carousel slide carousel-fade card-body p-0 h-100"
                            data-bs-touch="false"
                            data-bs-interval="false"
                        >
                            <div className="carousel-inner h-100">
                                {
                                    imageFile.map((item, idx) => (
                                        <div key={idx} className={curImageIdx === idx ? 'carousel-item h-100 active' : 'carousel-item h-100'}>
                                            <img
                                                src={item.viewImage}
                                                className="d-block w-100 h-100"
                                                alt="Feed Images"
                                            />
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
                    }
                </Card>
                <div className="m-2">
                {
                    imageFile.length > 0 &&
                    <button className="btn btn-outline-dark" onClick={() => fileInput.current.click()}>파일재등록</button>
                }
                </div>
                <div>
                    <textarea 
                        className="form-control"
                        placeholder="게시글 작성" 
                        name="title" 
                        rows={10}
                        value={feed.title} 
                        onChange={handleData}
                        style={{resize:"none"}}
                    />
                </div>
                <div>
                    <input 
                        type="file" 
                        ref={fileInput} 
                        className="form-control" 
                        multiple={true} 
                        name="files" 
                        onChange={onImageData} 
                        style={{display:"none"}}
                    />
                </div>
                {/* TODO: tagId */}
                <div>
                    <button className="btn btn-outline-primary" onClick={registerFeed}>피드 등록</button>
                </div>
            </div>
        </div>
    );
}

export default FeedRegister;