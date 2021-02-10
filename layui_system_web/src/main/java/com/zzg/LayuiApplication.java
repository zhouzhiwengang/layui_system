package com.zzg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.zzg.common.util.SpringContextUtil;

@SpringBootApplication
@MapperScan("com.zzg.mapper")
public class LayuiApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext applicationContext = SpringApplication.run(LayuiApplication.class, args);
		SpringContextUtil.setApplicationContext(applicationContext);
		System.out.println("============= SpringBoot Layui Start Start Success =============");
	}

}
