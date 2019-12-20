package com.briup.environment.clientimpl;

// 客户端的网络模块实现类
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.briup.environment.bean.Environment;
import com.briup.environment.client.Client;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationAware;
import com.briup.environment.util.Log;
import com.briup.environment.util.LogImpl;

public class ClientImpl implements Client,ConfigurationAware {
	private int port;
	private String ip;
	private Configuration conf;
	
	public void setConfiguration(Configuration conf) {
		this.conf = conf;
	}
	//private Logger log = new LogImpl("kehuduan");
	@Override
	public void init(Properties pro) throws Exception {
		port =Integer.parseInt(pro.getProperty("port"));
		ip = pro.getProperty("ip");
		//System.out.println(ip + " "+ port);
	}

	@Override
	public void send(Collection<Environment> args0) throws Exception {

		Socket s = null;
		s = new Socket(ip, port);
	//	log.info("成功与服务器连接。");
		// 2. 封装流对象
		OutputStream out = s.getOutputStream();

	//	log.info("发送数据给服务器");
		ObjectOutputStream oos = new ObjectOutputStream(out);
		oos.writeObject(args0);
	//	log.info("发送结束");
		// 4. 释放资源
		oos.flush();
		oos.close();
		out.close();
		s.close();
	}



}
