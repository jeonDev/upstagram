package com.api.upstagram;

import com.api.upstagram.domain.Feed.Repository.FeedDslRepository;
import com.api.upstagram.vo.Feed.FeedRVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UpstagramApplicationTests {

	@Autowired
	private FeedDslRepository feedDslRepository;
	@Test
	void contextLoads() {
		List<FeedRVO> list = feedDslRepository.selectFeedList("user");

		for(FeedRVO data : list) {
			System.out.println(data.toString());
		}
	}

}
