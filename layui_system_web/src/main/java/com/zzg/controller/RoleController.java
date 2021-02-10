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
import com.zzg.entity.BaoanRole;
import com.zzg.service.BaoanRoleService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/role")
public class RoleController extends AbstractController {
	@Autowired
	private BaoanRoleService baoanRoleService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;

	@Override
	public void buildQuery(Map<String, Object> param, Object query) {
		// TODO Auto-generated method stub
		if (query != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			QueryWrapper<BaoanRole> queryWrapper = (QueryWrapper<BaoanRole>) query;
			if (param.get("name") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("name")))) {
					queryWrapper.like("name_", param.get("name"));
				}
			}
		}
	}

	// 增
	@ApiOperation(httpMethod = "POST", value = "角色保存")
	@RequestMapping(value = "/insert", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result insert(@RequestBody BaoanRole entity) {
		String sid = entity.getId();
		if (StringUtils.isEmpty(sid)) {
			sid = String.valueOf(idGenerator.nextId());
			entity.setId(sid);
		}
		boolean target = baoanRoleService.save(entity);
		if (target) {
			return Result.ok().setDatas("id", sid);
		} else {
			return Result.error("角色保存失败");
		}
	}

	// 改
	@ApiOperation(httpMethod = "POST", value = "角色修改")
	@RequestMapping(value = "/update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result update(@RequestBody BaoanRole entity) {
		boolean target = baoanRoleService.updateById(entity);
		if (target) {
			return Result.ok();
		} else {
			return Result.error("角色更新失败");
		}
	}

	// 删
	@ApiOperation(httpMethod = "POST", value = "角色删除")
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result delete(@RequestBody BaoanRole entity) {
		String sid = entity.getId();
		if (StringUtils.isNotEmpty(sid)) {
			baoanRoleService.removeById(sid);
			return Result.ok();
		}
		return Result.error("请求参数缺失Sid");
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "用户查询指定角色id")
	@RequestMapping(value = "/getSid", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result getUserByName(@RequestBody BaoanRole entity) {
		String sid = entity.getId();
		if (StringUtils.isNotEmpty(sid)) {
			QueryWrapper<BaoanRole> query = new QueryWrapper<BaoanRole>();
			query.eq("id", sid);
			return Result.ok().setDatas(baoanRoleService.getOne(query));
		}
		return Result.error("请求参数缺失Sid");

	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件角色记录")
	@RequestMapping(value = "/getList", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "管理员名称", required = false, dataType = "String", paramType = "query") })
	public Result getList(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<BaoanRole> query = new QueryWrapper<BaoanRole>();
		this.buildQuery(parame, query);

		List<BaoanRole> list = baoanRoleService.list(query);
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "基于分页查询符合条件角色记录")
	@RequestMapping(value = "/getPage", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "管理员名称", required = false, dataType = "String", paramType = "query") })
	public Result getPage(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<BaoanRole> query = new QueryWrapper<BaoanRole>();
		this.buildQuery(parame, query);

		PageParame pageParame = this.initPageBounds(parame);
		Page<BaoanRole> page = new Page<BaoanRole>(pageParame.getPage(), pageParame.getLimit());
		IPage<BaoanRole> list = baoanRoleService.page(page, query);
		return Result.ok().setDatas(list);
	}

}
