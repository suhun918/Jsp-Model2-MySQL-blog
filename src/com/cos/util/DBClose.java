package com.cos.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBClose {

	/*
	 * DML(INSERT, UPDATE, DELETE) CLOSE 
	 */
	public static void close(Connection conn, PreparedStatement pstmt) {
			try {
				conn.close();
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	/*
	 * 오버로딩
	 * DQL(SELECT) CLOSE 
	 */
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
			try {
				conn.close();
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
}
