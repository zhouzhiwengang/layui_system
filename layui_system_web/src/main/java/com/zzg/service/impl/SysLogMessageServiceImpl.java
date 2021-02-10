package com.zzg.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzg.entity.SysLogMessage;
import com.zzg.mapper.SysLogMessageMapper;
import com.zzg.service.SysLogMessageService;

@Service
public class SysLogMessageServiceImpl extends ServiceImpl<SysLogMessageMapper, SysLogMessage> implements SysLogMessageService {

}
