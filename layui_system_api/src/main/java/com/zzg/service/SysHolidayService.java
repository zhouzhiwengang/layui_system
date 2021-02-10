package com.zzg.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzg.entity.SysHoliday;

public interface SysHolidayService extends IService<SysHoliday> {
	List<String> selectGroupByYear();
}
