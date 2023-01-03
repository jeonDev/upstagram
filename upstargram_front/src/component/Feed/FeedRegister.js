import React, {useEffect, useRef, useState} from "react";
import { feedRegister } from "../../api/FeedApi";
import "../../assets/css/Main.css";


const FeedRegister = () => {

    const fileInput = useRef("");
    const [feed, setFeed] = useState({
       title    : '',
       hashtag  : '',
       useYn    : '',
       tagId    : []
    });
    const [imageFile, setImageFile] = useState([]);

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

        for(let i = 0; i < fileSize; i++) {
            console.log(fileList[i]);
            setImageFile(imageFile => [
                ...imageFile,
                fileList[i].file
            ])
        }
    }

    // 피드 등록
    const registerFeed = async () => {
        let formData = new FormData();

        const hashtag = extraHashtags(feed.title);
        setFeed({
            ...feed,
            hashtag : hashtag
        });

        formData.append('files', imageFile);
        formData.append('pvo', new Blob([JSON.stringify(feed)], {type: "application/json"}));

        const result = await feedRegister(formData);

        if(result.code === 200) {
            console.log('성공');
        } else {
            console.log('실패')
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


    return (
        <div className={"container bg-light p-2 mt-3"}>
            <div>
                <div>
                    <textarea 
                        className="form-control"
                        placeholder="게시글 작성" 
                        name="title" 
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
                        value={feed.files} 
                        onChange={onImageData} 
                        style={{display:"none"}}
                    />
                    <button className="btn btn-outline-dark" onClick={() => fileInput.current.click()}>파일업로드</button>
                </div>
                <div>
                    <button className="btn btn-outline-primary" onClick={registerFeed}>피드 등록</button>
                </div>
            </div>
        </div>
    );
}

export default FeedRegister;