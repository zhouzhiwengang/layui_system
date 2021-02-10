package com.zzg.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzg.entity.SysLogRecord;

public interface SysLogRecordService extends IService<SysLogRecord> {
	List<String> selectGroupByYear();
}
