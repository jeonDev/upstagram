package com.api.upstagram.vo.Story;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class StoryPVO {
    private String id;
    private String storyFileName;
    private String storyTime;
    private String showYn;
    private String keepYn;
    private MultipartFile file;
}
