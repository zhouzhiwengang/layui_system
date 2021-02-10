package com.zzg.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzg.entity.BaoanPrivilege;
import com.zzg.vo.BaoanPrivilegeVo;

public interface BaoanPrivilegeMapper extends BaseMapper<BaoanPrivilege> {
	List<BaoanPrivilegeVo> selectList(Map<String, Object> parameter);
	
	IPage<BaoanPrivilegeVo> selectPage(Page page, @Param("vo")Map<String, Object> parameter);
}
