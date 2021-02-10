package com.zzg.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzg.entity.BaoanPrivilege;
import com.zzg.mapper.BaoanPrivilegeMapper;
import com.zzg.service.BaoanPrivilegeService;
import com.zzg.vo.BaoanPrivilegeVo;

@Service
public class BaoanPrivilegeServiceImpl extends ServiceImpl<BaoanPrivilegeMapper, BaoanPrivilege> implements BaoanPrivilegeService {
	@Autowired
	private BaoanPrivilegeMapper mapper;
	
	@Override
	public List<BaoanPrivilegeVo> selectList(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return mapper.selectList(parameter);
	}

	@Override
	public IPage<BaoanPrivilegeVo> selectPage(Page<BaoanPrivilegeVo> page, Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return mapper.selectPage(page, parameter);
	}

}
