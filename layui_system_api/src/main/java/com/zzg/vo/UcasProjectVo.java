package com.zzg.vo;

import com.zzg.entity.UcasProject;
import com.zzg.entity.UcasUnit;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class UcasProjectVo extends UcasProject {
	 // 施工单位
	 private UcasUnit consUnit;
	 // 建设单位
	 private UcasUnit buildUnit;
	 // 监理单位
	 private UcasUnit qualitySupervisionUnit;
	 // 勘察单位
	 private UcasUnit reconUnit;
	 // 设计单位
	 private UcasUnit designUnit;

}
