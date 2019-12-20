package com.briup.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * 		定义输入值的设置的标准
 */
public interface PrepareSetter {
	public void setter(PreparedStatement pst) throws SQLException;
}
