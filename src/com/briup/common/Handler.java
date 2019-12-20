package com.briup.common;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 		结果集处理标准
 */
public interface Handler {
	public void handler(ResultSet rs) throws SQLException;
}
