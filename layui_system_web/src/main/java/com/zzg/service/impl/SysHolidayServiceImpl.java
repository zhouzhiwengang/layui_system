package com.zzg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzg.entity.SysHoliday;
import com.zzg.mapper.SysHolidayMapper;
import com.zzg.service.SysHolidayService;

@Service
public class SysHolidayServiceImpl extends ServiceImpl<SysHolidayMapper, SysHoliday> implements SysHolidayService {
	@Autowired
	SysHolidayMapper mapper;
	
	@Override
	public List<String> selectGroupByYear() {
		// TODO Auto-generated method stub
		return mapper.selectGroupByYear();
	}

}
