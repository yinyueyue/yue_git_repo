package com.pax.sms;

import com.pax.sms.core.utils.EntityMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@EnableScheduling  //启动定时任务扫描
@ServletComponentScan
@EnableCaching
@SpringBootApplication
@EnableTransactionManagement  // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan(basePackages = "com.pax.sms.*.dao", markerInterface = EntityMapper.class)
public class SmsApplication  {
	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);
	}
}
