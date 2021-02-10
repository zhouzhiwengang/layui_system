package com.zzg.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "sys_holiday")
@Data
public class SysHoliday implements Serializable {
	private String sid;

	private String year;

	private String holiday;
}
