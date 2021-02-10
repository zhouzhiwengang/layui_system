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
import com.zzg.entity.BaoanPrivilege;
import com.zzg.service.BaoanPrivilegeService;
import com.zzg.vo.BaoanPrivilegeVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/baoan/privilege")
public class BaoanPrivilegeController extends AbstractController {

	@Autowired
	private BaoanPrivilegeService baoanPrivilegeService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;

	@Override
	public void buildQuery(Map<String, Object> param, Object query) {
		// TODO Auto-generated method stub
		if (query != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			QueryWrapper<BaoanPrivilegeVo> queryWrapper = (QueryWrapper<BaoanPrivilegeVo>) query;
			if (param.get("id") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("id")))) {
					queryWrapper.eq("id", param.get("id"));
				}
			}
			if (param.get("pid") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("pid")))) {
					queryWrapper.eq("pid", param.get("pid"));
				}
			}
		}
	}

	// 增
	@ApiOperation(httpMethod = "POST", value = "权限保存")
	@RequestMapping(value = "/insert", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result insert(@RequestBody BaoanPrivilege entity) {
		String sid = entity.getId();
		if (StringUtils.isEmpty(sid)) {
			sid = String.valueOf(idGenerator.nextId());
			entity.setId(sid);
		}
		boolean target = baoanPrivilegeService.save(entity);
		if (target) {
			return Result.ok().setDatas("id", sid);
		} else {
			return Result.error("权限保存失败");
		}
	}

	// 改
	@ApiOperation(httpMethod = "POST", value = "权限修改")
	@RequestMapping(value = "/update", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result update(@RequestBody BaoanPrivilege entity) {
		boolean target = baoanPrivilegeService.updateById(entity);
		if (target) {
			return Result.ok();
		} else {
			return Result.error("权限更新失败");
		}
	}

	// 删
	@ApiOperation(httpMethod = "POST", value = "权限删除")
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result delete(@RequestBody BaoanPrivilege entity) {
		String sid = entity.getId();
		if (StringUtils.isNotEmpty(sid)) {
			baoanPrivilegeService.removeById(sid);
			return Result.ok();
		}
		return Result.error("请求参数缺失Sid");
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "用户查询指定权限id")
	@RequestMapping(value = "/getSid", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result getUserByName(@RequestBody BaoanPrivilege entity) {
		String sid = entity.getId();
		if (StringUtils.isNotEmpty(sid)) {
			QueryWrapper<BaoanPrivilege> query = new QueryWrapper<BaoanPrivilege>();
			query.eq("id", sid);
			return Result.ok().setDatas(baoanPrivilegeService.getOne(query));
		}
		return Result.error("请求参数缺失Sid");

	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件权限记录")
	@RequestMapping(value = "/getList", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "管理员名称", required = false, dataType = "String", paramType = "query") })
	public Result getList(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<BaoanPrivilege> query = new QueryWrapper<BaoanPrivilege>();
		this.buildQuery(parame, query);

		List<BaoanPrivilege> list = baoanPrivilegeService.list(query);
		return Result.ok().setDatas(list);
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "基于分页查询符合条件权限记录")
	@RequestMapping(value = "/getPage", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "username", value = "管理员名称", required = false, dataType = "String", paramType = "query") })
	public Result getPage(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
//		QueryWrapper<BaoanPrivilegeVo> query = new QueryWrapper<BaoanPrivilegeVo>();
//		this.buildQuery(parame, query);

		PageParame pageParame = this.initPageBounds(parame);
		Page<BaoanPrivilegeVo> page = new Page<BaoanPrivilegeVo>(pageParame.getPage(), pageParame.getLimit());
		IPage<BaoanPrivilegeVo> list = baoanPrivilegeService.selectPage(page, parame);
		return Result.ok().setDatas(list);
	}

}
