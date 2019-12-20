package com.briup.environment.util;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class LogImpl implements Log,ConfigurationAware{
	
	static {
		PropertyConfigurator.configure("file/log4j.properties");
	}
	private Configuration conf;
	private Logger logger = Logger.getLogger("日志");
	//private String pathFile;
	public LogImpl() {
	}
	public LogImpl(String name) {
		this.logger = logger.getLogger(name);
	}
	
	
	@Override
	public void init(Properties pro) throws Exception {
		//pathFile = pro.getProperty("log-properties");
	}

	@Override
	public void debug(String arg0) {
		logger.debug(arg0);
	}

	@Override
	public void error(String arg0) {
		logger.error(arg0);
	}

	@Override
	public void fatal(String arg0) {
		logger.fatal(arg0);
	}

	@Override
	public void info(String arg0) {
		logger.info(arg0);
	}

	@Override
	public void warn(String arg0) {
		logger.warn(arg0);
	}
	@Override
	public void setConfiguration(Configuration conf) {
		this.conf = conf;
	}

}
