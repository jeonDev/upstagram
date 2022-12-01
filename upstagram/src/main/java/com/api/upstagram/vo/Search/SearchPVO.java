package com.api.upstagram.vo.Search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SearchPVO {
    private String searchDivisionCode;      // 1 : 사용자 , 2 : 해시태그
    private String searchValue;
    private String id;
}
