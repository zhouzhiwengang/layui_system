package com.zzg.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@SuppressWarnings("serial")
@TableName(value = "baoan_role")
@Data
public class BaoanRole implements Serializable{
	private String id;

	@TableField(value="name_")
    private String name;

	@TableField(value="desc_")
    private String desc;

    private Boolean enabled;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}
