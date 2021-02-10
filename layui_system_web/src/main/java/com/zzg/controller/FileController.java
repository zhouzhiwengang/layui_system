package com.zzg.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzg.common.constants.FileUploadConstant;
import com.zzg.common.controller.AbstractController;
import com.zzg.common.entity.ChunkInfoModel;
import com.zzg.common.entity.PageParame;
import com.zzg.common.entity.Result;
import com.zzg.component.fileupload.AttachmentComponent;
import com.zzg.entity.SysEfileInfo;
import com.zzg.entity.SysEfileStore;
import com.zzg.service.SysEfileInfoService;
import com.zzg.service.SysEfileStoreService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/file")
public class FileController extends AbstractController  {
	// 日志记录
	public static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private AttachmentComponent upload;
	@Autowired
	private SysEfileInfoService sysEfileInfoService;
	@Autowired
	private SysEfileStoreService sysEfileStoreService;

	/**
	 * 通用文件上传功能; 备注：文件大小<=30M,如果超出规定文件大小,建议采用大文件上传
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/fileUpload", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "文件上传(小于等于30M)")
	public Result upload(ChunkInfoModel entity) {
		if (logger.isDebugEnabled()) {
			logger.debug(entity.toString());
		}
		SysEfileInfo model = null;
		try {
			model = upload.smallAttachUpload(entity, "default");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 logger.error("error: {}", e.getMessage(), e);
		}
		return Result.ok("文件上传成功").setDatas("model", model);
	}

	/**
	 * 通用文件删除功能
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/fileDelete", method = { RequestMethod.POST })
	@ResponseBody
	@ApiOperation(httpMethod = "POST", value = "附件删除")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "efileSid", value = "efileSid", required = false, dataType = "String", paramType = "query") })
	public Result fileDelete(
			@RequestBody @ApiParam(name = "JSONObject对象", value = "json格式对象", required = true) Map<String, Object> parameter) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.valueOf(parameter.get("sid")));
		}
		if (parameter.get("sid") != null) {
			String sid = String.valueOf(parameter.get("sid"));
			if (StringUtils.isNotBlank(sid)) {
				sysEfileInfoService.removeById(sid);
				return Result.ok("文件删除成功").setDatas("efileSid", sid);
			}
		}
		return Result.error("请求参数sid缺失");

	}

	/**
	 * 通用大文件分块上传; 设计思路：大文件按照指定文件大小分割成大小统一的文件块,文件块生成的规则是按数字递增生成创建。
	 * 
	 * @param entity
	 * @return
	 */
	@ApiOperation(httpMethod = "POST", value = "文件块上传")
	@RequestMapping(value = "/fileBlockUpload", method = { RequestMethod.POST })
	@ResponseBody
	public Result blockUpload(ChunkInfoModel entity) {
		if (logger.isDebugEnabled()) {
			logger.debug(entity.toString());
		}
		Result result = null;
		try{
			result = upload.attachBlockUpload(entity, "bigFile");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("error{}", e.getMessage(), e);
		}
		return result;

	}

	/**
	 * 通用大文件分块合并; 设计思路:文件块按照数字递增的顺序合并为大文件
	 * 
	 * @param entity
	 * @return
	 */
	@ApiOperation(httpMethod = "POST", value = "文件块合并")
	@RequestMapping(value = "/fileMerge", method = { RequestMethod.POST })
	@ResponseBody
	public Result merge(ChunkInfoModel entity) {
		if (logger.isDebugEnabled()) {
			logger.debug(entity.toString());
		}
		SysEfileInfo model = null;
		try {
			model = upload.bigAttachMerge(entity, "bigFile");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("error{}", e.getMessage(), e);
		}
		;

		return Result.ok("文件合并成功").setDatas("model", model);
	}

	/**
	 * 通用大文件 暂停/重新上传功能; 设计思路：返回大文件已经正常上传完成的文件块信息。
	 * 
	 * @param entity
	 * @return
	 */
	@ApiOperation(httpMethod = "POST", value = "文件上传记录")
	@RequestMapping(value = "/find", method = { RequestMethod.POST })
	@ResponseBody
	public Result find(ChunkInfoModel entity) {
		if (logger.isDebugEnabled()) {
			logger.debug(entity.toString());
		}
		Result result = null;
		try{
			result = upload.breakRenewal(entity, "bigFile");
		} catch(Exception e){
			e.printStackTrace();
			logger.error("error{}", e.getMessage(), e);
		}
		return result;
	}

	// 查
	@ApiOperation(httpMethod = "POST", value = "查询符合条件上传文件记录")
	@RequestMapping(value = "/getList", method = { RequestMethod.POST })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "businessSid", value = "业务Sid", required = false, dataType = "String", paramType = "query") })
	public Result getList(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<SysEfileInfo> query = new QueryWrapper<SysEfileInfo>();
		this.buildQuery(parame, query);

		List<SysEfileInfo> list = sysEfileInfoService.list(query);
		return Result.ok().setDatas(list);
	}
	
	// 查
	@ApiOperation(httpMethod = "POST", value = "基于分页查询符合条件上传文件记录")
	@RequestMapping(value = "/getPage", method = { RequestMethod.POST })
	@ApiImplicitParams({
		@ApiImplicitParam(name = "businessSid", value = "业务Sid", required = false, dataType = "String", paramType = "query")
	})
	public Result getPage(@RequestBody Map<String, Object> parame) {
		// 动态构建添加参数
		QueryWrapper<SysEfileInfo> query = new QueryWrapper<SysEfileInfo>();
		this.buildQuery(parame, query);

		PageParame pageParame = this.initPageBounds(parame);
		Page<SysEfileInfo> page = new Page<SysEfileInfo>(pageParame.getPage(), pageParame.getLimit());
		IPage<SysEfileInfo> list = sysEfileInfoService.page(page, query);
		
		QueryWrapper<SysEfileStore> queryWrapper = new QueryWrapper<SysEfileStore>();
		queryWrapper.eq("store_code", FileUploadConstant.EFILE_UPLOAD_DIR_PATH);
		Map<String, Object> map = sysEfileStoreService.getMap(queryWrapper);
		String pathPrefix = String.valueOf(map.get(FileUploadConstant.IDENTIFIER));
		
		list.getRecords().stream().filter(item->{
			return StringUtils.isNotEmpty(item.getFilePath());
		}).forEach(item->{
			String filePath = item.getFilePath().replace(pathPrefix.concat("\\"), "");
			item.setFilePath(filePath);
		});
		
		return Result.ok().setDatas(list);
	}


	// 删
	@ApiOperation(httpMethod = "POST", value = "上传文件记录删除")
	@RequestMapping(value = "/delete", method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public Result delete(@RequestBody SysEfileInfo entity) {
		String sid = entity.getSid();
		if (StringUtils.isNotEmpty(sid)) {
			QueryWrapper<SysEfileInfo> query = new QueryWrapper<SysEfileInfo>();
			query.eq("sid", entity.getSid());
			sysEfileInfoService.remove(query);
			return Result.ok();
		}
		return Result.error("请求参数缺失Sid");
	}

	@Override
	public void buildQuery(Map<String, Object> param, Object query) {
		// TODO Auto-generated method stub
		if (query != null) {
			QueryWrapper<SysEfileInfo> queryWrapper = (QueryWrapper<SysEfileInfo>) query;
			if (param.get("businessSid") != null) {
				if (StringUtils.isNotBlank(String.valueOf(param.get("businessSid")))) {
					queryWrapper.eq("business_sid", param.get("businessSid"));
				}
			}
		}
	}

}
