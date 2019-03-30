package com.itself.util;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

public class MyBeanUtils {
	public static void populate2(Object obj, Map<String, Object> map) {
		try {
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday()方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	//BeanUtil找到User.class文件上有setBirthday方法，要执行，将"1998-7-2"转换为时间日期类型
	//BeanUtil不知道这个字符串的时间格式是什么？以下三行代码设置时间转换格式
	public static void populate(Object obj, Map<String, String[]> map) {
		try {
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday()方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			BeanUtils.populate(obj, map);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static<T> T populate(Class<T> clazz, Map<String, String[]> map) {
		try {
			
			T obj=clazz.newInstance();
			
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			//这个方法会遍历map<key, value>中的key，如果bean中有这个属性，就把这个key对应的value值赋给bean的属性。
			BeanUtils.populate(obj, map);
			
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
}
