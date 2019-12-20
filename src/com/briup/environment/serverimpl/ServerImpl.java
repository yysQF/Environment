package com.briup.environment.serverimpl;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.environment.bean.Environment;
import com.briup.environment.server.DBStore;
import com.briup.environment.server.Server;
import com.briup.environment.util.Configuration;
import com.briup.environment.util.ConfigurationAware;
import com.briup.environment.util.Log;
import com.briup.environment.util.LogImpl;

public class ServerImpl implements Server ,ConfigurationAware{
	private int port;
	private static String propath;
	private Configuration conf;
	private Log log = new LogImpl();
//	private Log log2 = conf.getLogger();
	

	@Override
	public void setConfiguration(Configuration conf) {
		this.conf = conf;
	}
	
	@Override
	public void init(Properties pro) throws Exception {
		port = Integer.parseInt(pro.getProperty("port"));
		propath = pro.getProperty("log-properties");
	}

	@Override
	public Collection<Environment> reciver() throws Exception {
		
		ServerSocket ss = null;
		Socket s = null;
		
		// 1.绑定端口，创建服务器端Socket
		ss = new ServerSocket(port);
		log.info("服务器启动......");
		// 2. 获取客户端Socket
		s = ss.accept();
		log.info("客户端连接成功！");
		// 3.获取基本流，封装流
		InputStream is = s.getInputStream();

		// 4.读写操作
		ObjectInputStream ois = new ObjectInputStream(is);
		log.info("准备接受");
		Collection<Environment> coll = (Collection<Environment>) ois.readObject();
		log.info("接受完毕");
		log.info("成功接收"+coll.size()+"条数据");
		// 5. 释放资源

		ois.close();
		is.close();
//		DBStore db = conf.getDbStore();
//		db.saveDb(coll);
//		log.info("入库完成");
		s.close();
		ss.close();

		return coll;
	}

	@Override
	public void shutdown() {
	}


}
