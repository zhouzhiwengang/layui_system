package com.zzg.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzg.common.controller.AbstractController;
import com.zzg.common.entity.PageParame;
import com.zzg.common.entity.Result;
import com.zzg.common.uuid.SnowflakeIdGenerator;
import com.zzg.entity.BaoanAdmin;
import com.zzg.service.BaoanAdminService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/admin")
public class AdminController extends AbstractController {

	@Autowired
	private BaoanAdminService baoanAdminService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;

	@Override
	public void buildQuery(Map<String, Object> param, Object query) {
		if (query != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			QueryWrapper<BaoanAdmin> queryWrapper = (QueryWrapper<BaoanAdmin>) query;
			if (param.get("username") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("username")))) {
					queryWrapper.like("username", param.get("username"));
				}
			}
		}
	}

	// 增
	@ApiOperation(httpMethod = "POST", value = "管理员保存")
	@RequestMapping(value = "/insert", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result insert(@RequestBody BaoanAdmin entity) {
		String sid = entity.getId();
		if (StringUtils.isEmpty(sid)) {
			sid = String.valueOf(idGenerator.nextId());
			entity.setId(sid);
		}
		boolean target = baoanAdminService.save(entity);
		if (target) {
			return Result.ok().setDatas("id", sid);
		} else {
			return Result.error("管理员保存失败");
		}
	}

	// 改
	@ApiOperation(httpMethod = "POST", value = "管理员修改")
	@RequestMapping(value = "/update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result update(@RequestBody BaoanAdmin entity) {
		boolean target = baoanAdminService.updateById(entity);
		if (target) {
			return Result.ok();
		} else {
			return Result.error("管理员更新失败");
		}
	}

	// 删
	@ApiOperation(httpMethod = "POST", value = "管理员删除")
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result delete(@RequestBody BaoanAdmin entity) {
		String sid = entity.getId();
		if (StringUtils.isNotEmpty(sid)) {
			baoanAdminService.removeById(sid);
			return Result.ok();
		}
		return Result.error("请求参数缺失Sid");
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "用户查询指定管理员id")
	@RequestMapping(value = "/getSid", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result getUserByName(@RequestBody BaoanAdmin entity) {
		String sid = entity.getId();
		if (StringUtils.isNotEmpty(sid)) {
			QueryWrapper<BaoanAdmin> query = new QueryWrapper<BaoanAdmin>();
			query.eq("id", sid);
			return Result.ok().setDatas(baoanAdminService.getOne(query));
		}
		return Result.error("请求参数缺失Sid");

	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件管理员记录")
	@RequestMapping(value = "/getList", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "管理员名称", required = false, dataType = "String", paramType = "query")
	})
	public Result getList(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<BaoanAdmin> query = new QueryWrapper<BaoanAdmin>();
		this.buildQuery(parame, query);

		List<BaoanAdmin> list = baoanAdminService.list(query);
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "基于分页查询符合条件管理员记录")
	@RequestMapping(value = "/getPage", method = { RequestMethod.POST })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "username", value = "管理员名称", required = false, dataType = "String", paramType = "query")
	})
	public Result getPage(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<BaoanAdmin> query = new QueryWrapper<BaoanAdmin>();
		this.buildQuery(parame, query);

		PageParame pageParame = this.initPageBounds(parame);
		Page<BaoanAdmin> page = new Page<BaoanAdmin>(pageParame.getPage(), pageParame.getLimit());
		IPage<BaoanAdmin> list = baoanAdminService.page(page, query);
		return Result.ok().setDatas(list);
	}

}
