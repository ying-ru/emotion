package com.ericblue.mindstream.systemtray;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class JDBC {

	private Connection connection = null;
	private Statement stmt = null;
	
	public void insertTable(String NOWTIME, int low_ALPHA,int high_ALPHA, 
			int low_BETA, int high_BETA,int low_GAMMA, int high_GAMMA) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 2: 取得資料庫連結
			// "jdbc:mysql://localhost:8282/neurosky?user=root&password=8282";
			String url = "jdbc:mysql://sqadb.cuxcf7jbbgaj.ap-northeast-1.rds.amazonaws.com/"
					+ "emotion?user=sai523847&password=12345678&useUnicode=true"
					+ "&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
			connection = DriverManager.getConnection(url);
			
			// Step 3: 建立Statement物件
			stmt = connection.createStatement();
			
			// Step 4: 下SQL
			int i = stmt.executeUpdate("insert into brainwave values('"
					+ NOWTIME + "'," + low_ALPHA + "," + high_ALPHA + ","
					+ low_BETA + "," + high_BETA + "," + low_GAMMA + ","
					+ high_GAMMA + ")");
			System.out.println(i + "data update\n");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
