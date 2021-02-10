package com.zzg.common.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.SimpleTypeConverter;

import com.zzg.common.convert.DateEditor;

/**
 * spring type converter 
 * @author Administrator
 *
 */
public class SimpleTypeConverterUtil {
	public static final Logger log = LoggerFactory.getLogger(SimpleTypeConverterUtil.class);
	
	private static final SimpleTypeConverter typeConverterDelegate = new SimpleTypeConverter();
	static{
		typeConverterDelegate.registerCustomEditor(Date.class, new DateEditor());
	}
	
	/**
	 * @param <T>
	 * @param value  待转换值,一般字符串
	 * @param requiredType 转后类型类对象
	 * @return
	 */
	public static <T> T convertIfNecessary(Object value, Class<T> requiredType) {
		T rs = null;
		try {
			rs = typeConverterDelegate.convertIfNecessary(value, requiredType);
		} catch (Exception e) {
			log.info(e.getMessage());
			if(requiredType == int.class || requiredType == Integer.class){
				rs = (T)Integer.valueOf(0);
			}
		}
		return rs;
	}
	
}
