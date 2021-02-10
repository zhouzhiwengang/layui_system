package com.zzg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzg.entity.SysLogRecord;
import com.zzg.mapper.SysLogRecordMapper;
import com.zzg.service.SysLogRecordService;

@Service
public class SysLogRecordServiceImpl extends ServiceImpl<SysLogRecordMapper, SysLogRecord> implements SysLogRecordService {
	@Autowired
	private SysLogRecordMapper mapper;
	
	@Override
	public List<String> selectGroupByYear() {
		// TODO Auto-generated method stub
		return mapper.selectGroupByYear();
	}

}
