package com.zzg.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzg.entity.BaoanPrivilege;
import com.zzg.vo.BaoanPrivilegeVo;

public interface BaoanPrivilegeService extends IService<BaoanPrivilege> {
	List<BaoanPrivilegeVo> selectList(Map<String, Object> parameter);
	
	IPage<BaoanPrivilegeVo> selectPage(Page<BaoanPrivilegeVo> page, Map<String, Object> parameter);
}
