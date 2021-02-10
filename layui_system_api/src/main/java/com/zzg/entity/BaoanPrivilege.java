package com.zzg.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "baoan_privilege")
@Data
public class BaoanPrivilege implements Serializable {
	private String id;

    private String privilegeName;

    private String privilegeCode;

    private String pid;

    private String url;

    private Integer orderRank;

    private String privilegeType;

    private String privilegeDescription;

    private String state;

    private String createdBy;

    private Date createdDt;

    private Integer version;

    private String updatedBy;

    private Date updatedDt;

    private Integer iconName;

    private Integer deleteFlag;
}
