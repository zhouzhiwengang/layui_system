package com.zzg.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "sys_log_message")
@Data
public class SysLogMessage implements Serializable {
	private String sid;

	private String recordSid;

	private String state;

	private Date sendDt;

	private String content;

	private Date createDt;
}
