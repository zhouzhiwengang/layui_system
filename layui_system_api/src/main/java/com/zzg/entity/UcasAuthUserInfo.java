package com.zzg.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@SuppressWarnings("serial")
@TableName(value = "ucas_auth_user")
public class UcasAuthUserInfo implements java.io.Serializable {
	@TableId(value = "sid", type = IdType.INPUT)
	private String sid;

	@NotBlank(message="登入账户不能为空")
	@Length(min=1, max=21, message="登入账户名称过长")
	private String userPin;

	@NotBlank(message="名称不能为空")
	@Length(min=1, max=21, message="名称过长")
	private String userName;

	@NotBlank(message="密码不能为空")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	private String gender;

	private String tel;

	private String phone;

	private String email;

	@NotBlank(message="状态不能为空")
	private String state;

	private String createdBy;

	private Date createdDt;

	private Integer version;

	private String updatedBy;

	private Date updatedDt;

	private String zoneOrgCode;
	
	private String organiztionSid;

	private String value1;

	private String value2;

	private String value3;

	private Integer deleteFlag;

	private String sessionId;

	// 补全字段(用户类别:系统管理员\普通用户)
	private String userCategory;

	private String uniqueSid;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUserPin() {
		return userPin;
	}

	public void setUserPin(String userPin) {
		this.userPin = userPin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}

	public String getZoneOrgCode() {
		return zoneOrgCode;
	}

	public void setZoneOrgCode(String zoneOrgCode) {
		this.zoneOrgCode = zoneOrgCode;
	}

	public String getOrganiztionSid() {
		return organiztionSid;
	}

	public void setOrganiztionSid(String organiztionSid) {
		this.organiztionSid = organiztionSid;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserCategory() {
		return userCategory;
	}

	public void setUserCategory(String userCategory) {
		this.userCategory = userCategory;
	}

	public String getUniqueSid() {
		return uniqueSid;
	}

	public void setUniqueSid(String uniqueSid) {
		this.uniqueSid = uniqueSid;
	}
	
	
	
	

}
