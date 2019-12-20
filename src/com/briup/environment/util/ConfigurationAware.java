package com.briup.environment.util;
// 将配置模块的对象注入给其他模块
public interface ConfigurationAware {
	
	public void setConfiguration(Configuration conf);
}
