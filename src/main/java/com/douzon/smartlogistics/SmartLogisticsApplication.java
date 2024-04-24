package com.douzon.smartlogistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableScheduling //스케줄링을 위한 어노테이션
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600) //redis 사용
public class SmartLogisticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLogisticsApplication.class, args);
	}

}
