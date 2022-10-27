package com.api.upstagram.domain.Story;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.api.upstagram.common.vo.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "STORY_WATCHING")
@Entity
public class StoryWatchingEntity extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyWatchingNo;
    
    private Long storyNo;
    private String id;

    @Builder
    public StoryWatchingEntity(Long storyWatchingNo, Long storyNo, String id) {
        this.storyWatchingNo = storyWatchingNo;
        this.storyNo = storyNo;
        this.id = id;
    }
}
