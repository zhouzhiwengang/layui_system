package com.zzg.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zzg.common.controller.AbstractController;
import com.zzg.common.entity.Result;
import com.zzg.common.uuid.SnowflakeIdGenerator;
import com.zzg.entity.SysLogMessage;
import com.zzg.entity.SysLogRecord;
import com.zzg.service.SysLogMessageService;
import com.zzg.service.SysLogRecordService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/log/message")
public class LogMessageController extends AbstractController {

	@Autowired
	private SysLogMessageService sysLogMessageService;
	@Autowired
	private SysLogRecordService sysLogRecordService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;

	@Override
	public void buildQuery(Map<String, Object> param, Object query) {
		// TODO Auto-generated method stub
		if (query != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			QueryWrapper<SysLogMessage> queryWrapper = (QueryWrapper<SysLogMessage>) query;
			if (param.get("sid") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("sid")))) {
					queryWrapper.eq("sid", param.get("sid"));
				}
			}
			if (param.get("recordSid") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("recordSid")))) {
					queryWrapper.eq("record_sid", param.get("recordSid"));
				}
			}
		}
	}

	// 增
	@ApiOperation(httpMethod = "POST", value = "催缴日志信息保存")
	@RequestMapping(value = "/insert", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result insert(@RequestBody SysLogMessage entity) {
		String sid = entity.getSid();
		if (StringUtils.isEmpty(sid)) {
			sid = String.valueOf(idGenerator.nextId());
			entity.setSid(sid);
		}
		boolean target = sysLogMessageService.save(entity);
		if (target) {
			return Result.ok().setDatas("sid", sid);
		} else {
			return Result.error("催缴日志失败");
		}
	}

	// 改
	@ApiOperation(httpMethod = "POST", value = "催缴日志信息修改")
	@RequestMapping(value = "/update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result update(@RequestBody SysLogMessage entity) {
		boolean target = sysLogMessageService.updateById(entity);
		if (target) {
			return Result.ok();
		} else {
			return Result.error("催缴日志信息更新失败");
		}
	}

	// 删
	@ApiOperation(httpMethod = "POST", value = "催缴日志信息删除")
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result delete(@RequestBody SysLogMessage entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			sysLogMessageService.removeById(sid);
			return Result.ok();
		}
		return Result.error("请求参数缺失Sid");
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "用户查询指定催缴日志信息Sid")
	@RequestMapping(value = "/getSid", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result getUserByName(@RequestBody SysLogMessage entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			QueryWrapper<SysLogMessage> query = new QueryWrapper<SysLogMessage>();
			query.eq("sid", sid);
			return Result.ok().setDatas(sysLogMessageService.getOne(query));
		}
		return Result.error("请求参数缺失Sid");
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件催缴日志信息记录")
	@RequestMapping(value = "/getList", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "recordSid", value = "催缴日志Sid", required = false, dataType = "String", paramType = "query") })
	public Result getList(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<SysLogMessage> query = new QueryWrapper<SysLogMessage>();
		this.buildQuery(parame, query);

		List<SysLogMessage> list = sysLogMessageService.list(query);
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "催缴日志信息初始化")
	@RequestMapping(value = "/initData", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "recordSid", value = "催缴日志Sid", required = false, dataType = "String", paramType = "query") })
	public Result initData(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<SysLogRecord> query = new QueryWrapper<SysLogRecord>();
		List<SysLogRecord> list = sysLogRecordService.list(query);
		if(CollectionUtils.isNotEmpty(list)){
			list.stream().filter(item->{
				return item.getRecordDt() != null;
			}).forEach(item ->{
				// 备案日期
				Date date = item.getRecordDt();
				
			});
		}
		return Result.ok();
	}

}
