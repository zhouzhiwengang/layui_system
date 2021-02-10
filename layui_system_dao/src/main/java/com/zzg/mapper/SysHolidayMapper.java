package com.zzg.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzg.entity.SysHoliday;

public interface SysHolidayMapper extends BaseMapper<SysHoliday> {
	List<String> selectGroupByYear();
}
