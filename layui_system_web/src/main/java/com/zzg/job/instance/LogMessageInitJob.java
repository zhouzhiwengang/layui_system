package com.zzg.job.instance;

/**
 * 工程催缴消息记录生成
 */
//@ElasticSchedulerParam(cron = "0 14 18 * * ?", shardingTotalCount = 1, shardingItemParameters = "0=Core")
//@Component
//public class LogMessageInitJob implements SimpleJob {
//	private static final Logger log = LoggerFactory.getLogger(LogMessageInitJob.class);
//
//	@Autowired
//	private SysLogRecordService sysLogRecordService;
//	@Autowired
//	private SnowflakeIdGenerator idGenerator;
//	@Autowired
//	private SysLogMessageService sysLogMessageService;
//
//	@Override
//	public void execute(ShardingContext shardingContext) {
//		// TODO Auto-generated method stub
//		QueryWrapper<SysLogRecord> queryWrapper = new QueryWrapper<SysLogRecord>();
//		queryWrapper.eq("state", "1");
//		List<SysLogRecord> list = sysLogRecordService.list();
//		if (CollectionUtils.isNotEmpty(list)) {
//			list.stream().filter(item -> {
//				return item.getRecordDt() != null;
//			}).forEach(item -> {
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//				String startTime = format.format(item.getRecordDt());
//				
//				List<SysLogMessage> messages = new ArrayList<SysLogMessage>();
//				for(int i =1; i <= 5; i++){
//					String date = null;
//					SysLogMessage message = new SysLogMessage();
//					try {
//						date = isWeekendOrHoliday(startTime, i);
//						message.setSid(String.valueOf(idGenerator.nextId()));
//						message.setRecordSid(item.getSid());
//						message.setContent("消息模板待补充");
//						message.setCreateDt(new Date());
//						message.setSendDt(format.parse(date));
//						messages.add(message);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						log.error(String.valueOf(e));
//					}
//				}
//				if(CollectionUtils.isNotEmpty(messages)){
//					sysLogMessageService.saveBatch(messages);
//				}
//			});
//		}
//	}
//
//	public String isWeekendOrHoliday(String date, Integer type) throws ParseException {
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Date recorDate = format.parse(date);
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(recorDate);
//		switch (type) {
//		case 1:
//			// 日期添加:一周
//			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+7);
//			break;
//		case 2:
//			// 日期添加:一个月
//			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+1);
//			break;
//		case 3:
//			// 日期添加：三个月
//			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+3);
//			break;
//		case 4:
//			// 日期添加:六个月
//			cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+6);
//			break;
//		default:
//			// 默认当天
//		}
//		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//			cal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH)+1);
//			recorDate = cal.getTime();
//			date = format.format(recorDate);
//			return isWeekendOrHolidaySub(date);
//		}
//		recorDate = cal.getTime();
//		return format.format(recorDate);
//	}
//	
//	public String isWeekendOrHolidaySub(String date) throws ParseException {
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Date recorDate = format.parse(date);
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(recorDate);
//		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
//			cal.set(Calendar.DATE, cal.get(Calendar.DAY_OF_MONTH)+1);
//			recorDate = cal.getTime();
//			date = format.format(recorDate);
//			return isWeekendOrHolidaySub(date);
//		}
//		return date;
//	}
//}
