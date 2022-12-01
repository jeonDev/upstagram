package com.api.upstagram.vo.Search;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SearchRVO {
    private String id;
    private String name;
    private String nickname;
    private String sex;
    private String tel;
}
