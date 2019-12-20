package com.briup.environment.Test;


import com.briup.environment.bean.Environment;
import com.briup.environment.server.Server;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationImpl;

public class ServerTest {
	public static void main(String[] args) throws Exception {
		Configuration conf = new ConfigurationImpl();
		Server server  = conf.getServer();	
		System.out.println("== 数据开始接收 ==");
		for (Environment envir : server.reciver()) {
			System.out.println(envir);
		}
		System.out.println("== 数据接收完毕 ==");
	}
}
