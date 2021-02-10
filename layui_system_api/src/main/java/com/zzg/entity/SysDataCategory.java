package com.zzg.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "sys_data_category")
@Data
public class SysDataCategory implements Serializable {
    private String id;

    private String categoryCode;

    private String categoryName;

    private String parentId;

}