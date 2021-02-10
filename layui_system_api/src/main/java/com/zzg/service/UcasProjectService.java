package com.zzg.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzg.entity.UcasProject;
import com.zzg.vo.UcasProjectVo;

public interface UcasProjectService extends IService<UcasProject> {

	List<UcasProjectVo> selectList(Map<String, Object> parameter);
	
	IPage<UcasProjectVo> selectPage(Page<UcasProjectVo> page, Map<String, Object> parameter);
}
