package com.zzg.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "sys_data_dict")
@Data
public class SysDataDict implements Serializable {
    private String id;

    private String categoryId;

    private String categoryCode;

    private String categoryName;

    @TableField(value="key_")
    private String key;

    @TableField(value="value_")
    private String value;

    private String type;

    private String state;

    private String createdBy;

    private Date createdDt;

    private Integer version;

    private String updatedBy;

    private Date updatedDt;

    @TableField(value="comment_")
    private String comment;
}