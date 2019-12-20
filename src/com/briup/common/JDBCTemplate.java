package com.briup.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * 		模板类
 */
public class JDBCTemplate {
	private Connection conn;
	
	public JDBCTemplate() {
		this(null);
	}
	public JDBCTemplate(Connection conn) {
		if (conn == null) 
			conn = ConnectionFactory.getConnection();
			this.conn = conn;
		
	}
	// 没有输入值，没有结果集
	public void execute(String sql) {
		Statement st = null;
		try {
			st = conn.createStatement();
			st.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(null, st);
		}
	}

	// 有输入值，没有结果集
	public void execute(String sql, PrepareSetter setter) {
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			// 给占位符赋值
			if(setter != null) setter.setter(pst);
			pst.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(null, pst);
		}
	}

	// 没有输入值，有结果集
	public void execute(String sql, Handler handler) {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			// 
			if(handler != null) handler.handler(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(null, st, rs);
		}
	}

	// 有输入值，有结果集
	public void execute(String sql,PrepareSetter setter,Handler handler) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			// 给占位符赋值
			if(setter != null) setter.setter(pst);
			rs = pst.executeQuery();
			//rs =pst.getGeneratedKeys();
			if(handler != null) handler.handler(rs);
			pst.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(null, pst, rs);
		}

	}
}
