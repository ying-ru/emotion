package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class JDBC {

	private Connection connection = null;
	private Statement stmt = null;
	
	public void insert(String time, int lowAlpha,int highAlpha, 
			int lowBeta, int highBeta,int lowGamma, int highGamma) {
		try {
			// Step 1: 載入JDBC驅動程式: 請將正確值填入 ""中
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 2: 取得資料庫連結: 請將正確值填入 ""中
			// "jdbc:mysql://localhost:8282/neurosky?user=root&password=8282";
			String url = "jdbc:mysql://sqadb.cuxcf7jbbgaj.ap-northeast-1.rds.amazonaws.com/"
					+ "emotion?user=sai523847&password=12345678&useUnicode=true"
					+ "&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
			connection = DriverManager.getConnection(url);
			
			// Step 3: 建立Statement物件
			stmt = connection.createStatement();
			
			// Step 4: 下SQL
			int i = stmt.executeUpdate("insert into brainwave values('"
					+ time + "'," + lowAlpha + "," + highAlpha + ","
					+ lowBeta + "," + highBeta + "," + lowGamma + ","
					+ highGamma + ")");
			System.out.println(i + "data update");
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
	
	public int select(String time, String wave) {
		int value = 0;
		try {
			// Step 1: 載入JDBC驅動程式: 請將正確值填入 ""中
			Class.forName("com.mysql.jdbc.Driver");
			
			// Step 2: 取得資料庫連結: 請將正確值填入 ""中
			// "jdbc:mysql://localhost:8282/neurosky?user=root&password=8282";
			String url = "jdbc:mysql://sqadb.cuxcf7jbbgaj.ap-northeast-1.rds.amazonaws.com/"
					+ "emotion?user=sai523847&password=12345678&useUnicode=true"
					+ "&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
			connection = DriverManager.getConnection(url);
			
			// Step 3: 建立Statement物件
			stmt = connection.createStatement();
			
			// Step 4: 下SQL
			ResultSet rs = stmt.executeQuery("SELECT " + wave + " FROM brainwave "
				+ "WHERE time = " + time);
			rs.next();
			value = rs.getInt(1);
			System.out.println(wave + ": " + rs.getInt(1));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
}
