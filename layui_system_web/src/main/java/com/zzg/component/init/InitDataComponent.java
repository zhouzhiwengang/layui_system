package com.zzg.component.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzg.common.uuid.SnowflakeIdGenerator;
import com.zzg.entity.SysLogRecord;
import com.zzg.entity.UcasProject;
import com.zzg.entity.UcasUnit;
import com.zzg.service.SysLogRecordService;
import com.zzg.service.UcasProjectService;
import com.zzg.service.UcasUnitService;

/**
 * 异步任务：初始化数据
 * 
 * @author zzg
 *
 */
@Component
public class InitDataComponent {
	@Autowired
	private SysLogRecordService sysLogRecordService;
	@Autowired
	private SnowflakeIdGenerator idGenerator;
	@Autowired
	private UcasProjectService ucasProjectService;
	@Autowired
	private UcasUnitService ucasUnitService;

	@Async
	public void asyncBatchInit() {
		// java 手动分页核心代码
		Integer count = sysLogRecordService.count();
		int pageSize = 100;
		int pageNum = count / pageSize;
		int surplus = count % pageSize;// 是不是整除
		if (surplus > 0) {
			pageNum = pageNum + 1;
		}
		for(int i = 0; i < pageNum; i++){
			List<UcasProject> batchList = new ArrayList<UcasProject>();
	        QueryWrapper<SysLogRecord> query = new QueryWrapper<SysLogRecord>();
	        Page<SysLogRecord> page = new Page<SysLogRecord>(i, pageSize);
	        IPage<SysLogRecord> list = sysLogRecordService.page(page, query);
	        if(CollectionUtils.isNotEmpty(list.getRecords())){
	        	list.getRecords().stream().forEach(item->{
	        		UcasProject object = new UcasProject();
	        		object.setSid(String.valueOf(idGenerator.nextId()));
	        		// 备案日期
	        		object.setRecordDt(item.getRecordDt());
	        		// 施工许可证号
	        		object.setConsPermitNo(item.getConsPermitNo());
	        		// 竣工验收备案号
	        		object.setCompletedProjectNumber(item.getCompletedProjectNumber());
	        		// 施工工程名称
	        		object.setProjectName(item.getProjectName());
	        		// 竣工工程名称
	        		object.setCompletedProjectName(item.getCompletedProjectName());
	        		// 项目编号
	        		object.setItemNo(item.getItemNo());
	        		// 项目名称
	        		object.setItemName(item.getItemName());
	        		// 工程编号
	        		object.setProjectNo(item.getProjectNo());
	        		// 申报人
	        		object.setCompletedProjectPerson(item.getUnitDeclarer());
	        		// 申报人电话
	        		object.setCompletedProjectPersonTel(item.getUnitDeclarerTel());
	        		// 建设单位经办人
	        		object.setBuildUnitManager(item.getBuildUnitManager());
	        		// 复核人
	        		object.setReviewer(item.getReviewer());
	        		// 接收状态
	        		object.setAccepState("2");
	        		
	        		String buildUnit = item.getBuildUnitName();
	        		// 建设单位
	        		if(StringUtils.isNotEmpty(buildUnit)){
	        			object.setBuildUnitSid(this.unitType("buildUnit", buildUnit));
	        		}
	        		
	        		String designUnit = item.getDesignUnitName();
	        		// 设计单位
	        		if(StringUtils.isNotEmpty(designUnit)){
	        			object.setDesignUnitSid(this.unitType("designUnit", designUnit));
	        		}
	        		
	        		String consUnit = item.getConsUnitName();
	        		// 施工单位
	        		if(StringUtils.isNotEmpty(consUnit)){
	        			object.setConsUnitSid(this.unitType("consUnit", consUnit));
	        		}
	        		
	        		String qualitySupervisionUnit = item.getQualitySupervisionUnitName();
	        		// 监理单位
	        		if(StringUtils.isNotEmpty(qualitySupervisionUnit)){
	        			object.setQualitySupervisionUnitSid(this.unitType("qualitySupervisionUnit", qualitySupervisionUnit));
	        		}
	        		batchList.add(object);
	        	});
	        }
	        // 批量插入
	        if(CollectionUtils.isNotEmpty(batchList)){
	        	ucasProjectService.saveBatch(batchList);
	        }
	    }

	}
	
	public String unitType(String unitType, String unitName){
		QueryWrapper<UcasUnit> query = new QueryWrapper<UcasUnit>();
		query.eq("unit_type", unitType);
		query.eq("unit_name", unitName);
		UcasUnit object = ucasUnitService.getOne(query);
		if(object != null){
			return object.getSid();
		}
		String sid = String.valueOf(idGenerator.nextId());
		object = new UcasUnit();
		object.setUnitType(unitType);
		object.setUnitName(unitName);
		object.setSid(sid);
		ucasUnitService.save(object);
		return sid;
	}
}
