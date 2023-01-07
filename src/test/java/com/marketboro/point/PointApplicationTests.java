package com.marketboro.point;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;

//@SpringBootTest
class PointApplicationTests {

	@Test
	void contextLoads() {
		Long nowTime1 = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		System.out.println(nowTime1);
	}

}
