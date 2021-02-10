package com.zzg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zzg.component.init.InitDataComponent;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/initdate")
@EnableAsync  //启动SpringBoot 异步任务机制
public class InitDataController {
	@Autowired
	InitDataComponent initDataComponent;

	// 增
	@ApiOperation(httpMethod = "POST", value = "数据同步")
	@RequestMapping(value = "/batch", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public void batch() {
		initDataComponent.asyncBatchInit();
	}
}
