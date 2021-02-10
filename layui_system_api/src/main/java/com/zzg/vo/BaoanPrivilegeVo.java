package com.zzg.vo;

import java.util.List;

import com.zzg.entity.BaoanPrivilege;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class BaoanPrivilegeVo  extends BaoanPrivilege {
	
	private List<BaoanPrivilege> children;
}
