package com.briup.common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/*
 *  获取与数据库的连接对象
 */
public class ConnectionFactory {
	private static String driver;
	private static String url;
	private static String user;
	private static String password;

	static {

		Properties p = new Properties();
		try {
			p.load(ConnectionFactory.class.getResourceAsStream("db.properties"));
			// p.load(new FileInputStream("File/db.properties"))
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver = p.getProperty("driver");
		url = p.getProperty("url");
		user = p.getProperty("user");
		password = p.getProperty("password");

	}

	public static Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException("获取连接对象失败！！");
		}
		return conn;

	}
}
