package com.briup.environment.serverimpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.util.Calendar;
// 入库模块实现类
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.briup.environment.bean.Environment;
import com.briup.environment.server.DBStore;
import com.briup.environment.util.Log;
import com.briup.environment.util.LogImpl;

public class DBstoreImpl implements DBStore{
	private String driver;
	private String url;
	private String user;
	private String pwd;
	private int batchCount;

	private Log log = new LogImpl("入库操作");
	static {
		PropertyConfigurator.configure("src/com/briup/environment/serverimpl/log4j.properties");
	}
	
	@Override
	public void init(Properties pro) throws Exception {
		driver = pro.getProperty("driver");
		url = pro.getProperty("url");
		user = pro.getProperty("username");
		pwd = pro.getProperty("password");
		batchCount = Integer.parseInt(pro.getProperty("batch-size"));
	}

	@Override
	public void saveDb(Collection<Environment> coll) throws Exception {
		//将集合中的对象保存到数据库中，插入到对应的表中
//		Class.forName("com.mysql.cj.jdbc.Driver");
		Class.forName(driver);
//		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/envir?characterEncoding=utf8&useSSL=true&serverTimezone=UTC"
//										,"envir","123");
		Connection conn = DriverManager.getConnection(url,user,pwd);
		log.debug("连接对象："+conn);
		PreparedStatement pst = null;
		int mday = -1;//  上一条数据的日，解决pst对象过多的问题。
		int count = 0; // 统计sql 条数
		Calendar c = Calendar.getInstance();
		for (Environment envir : coll) {
			count++;
			
			c.setTime(new Date(envir.getGather_date().getTime()));
			int day = c.get(Calendar.DAY_OF_MONTH);
			if(mday != day) {
				if(pst!= null) {
					pst.addBatch();
					pst.close();
				}
				String sql = "insert into t_detail_"+day+" values(?,?,?,?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				mday = day;
			}
			pst.setString(1,envir.getName());
			pst.setString(2, envir.getSrcId());
			pst.setString(3, envir.getDstId());
			pst.setString(4, envir.getSersorAddress());
			pst.setInt(5, envir.getCount());
			pst.setString(6, envir.getCmd());
			pst.setInt(7, envir.getStatus());
			pst.setFloat(8, envir.getData());
			pst.setTimestamp(9, envir.getGather_date());
			
			pst.addBatch();
			if(count%1 == 0) {
				pst.executeBatch();
			}			
		}
		pst.executeBatch();
	}
}
