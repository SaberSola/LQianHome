package com.zl.lqian.base.context;

import com.zl.lqian.base.lang.Consts;
import com.zl.lqian.base.utils.PropertiesLoader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * LQianHome 全局配置类
 */
public class Global {
	
	/**
	 * 保存全局属性值
	 */
	private static ConcurrentMap<String, String> map = new ConcurrentHashMap<>();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader(Consts.MTONS_CONFIG);
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value);
		}
		return value;
	}
	
}
