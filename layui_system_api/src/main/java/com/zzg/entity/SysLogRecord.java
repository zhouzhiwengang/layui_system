package com.zzg.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "sys_log_record")
@Data
public class SysLogRecord implements Serializable {
	private String sid;

    private Date createDt;

    private String state;

    private Date recordDt;

    private String consPermitNo;

    private String completedProjectNumber;

    private String projectName;

    private String completedProjectName;

    private String itemNo;

    private String itemName;

    private String projectNo;

    private String unitDeclarer;

    private String unitDeclarerTel;

    private String buildUnitManager;

    private String reviewer;

    private String itemAddress;

    private String buildUnitName;

    private String designUnitName;

    private String consUnitName;
    
    private String qualitySupervisionUnitName;
    
    
}