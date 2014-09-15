package emotion.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	private String time;
	private double raiseHead, bodyStraighten, leftArms, rightArms;
	private Connection connection = null;
	private Statement stmt;
	
	public void connect() {
		try {
			// Step 1: 載入JDBC驅動程式: 請將正確值填入 ""中
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// Step 2: 取得資料庫連結: 請將正確值填入 ""中
            String url = "jdbc:mysql://localhost/emotion?user=root&password=pcroom";

//			String url = "jdbc:mysql://sqadb.cuxcf7jbbgaj.ap-northeast-1.rds.amazonaws.com/"
//					+ "emotion?user=sai523847&password=12345678&useUnicode=true"
//					+ "&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
			connection = DriverManager.getConnection(url);
//			createTable(); //if NEVER createTable
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void insert(String data) {
		connect();
		String insert = "INSERT INTO kinect VALUES (" + data + ")";
		try {
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(insert);
			System.out.println(result + " data was insert");
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
	}
	
	public void update(String time, double raiseHead, double bodyStraighten, double leftArms, double rightArms) {
		connect();
		this.time = time;
		String update = "UPDATE kinect "
				+ "SET raiseHead = " + raiseHead + ", bodyStraighten = " + bodyStraighten
				+ ", leftArms = " + leftArms + ", rightArms = " + rightArms
				+ " WHERE time = " + time;
		try {
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(update);
			System.out.println(result + " data was update");
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
	}
	
	public void delete(String time) {
		connect();
		this.time = time;
		String delete = "DELETE FROM kinect "
				+ "WHERE time = " + time;
		try {
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(delete);
			System.out.println(result + " data was delete");
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
	}
	
	public boolean selectTime(String time) { 
		connect();
		this.time = time;
		String selectTime = "SELECT time FROM kinect "
				+ "WHERE time = " + time;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectTime);
			rs.next();
			if (rs.getString(1) == null) {
				close();
				return false;
			} else {
				close();
				return true;
			}
			
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
			return false;
		}
	}
	
	public double selectRaiseHead(String time) {
		connect();
		this.time = time;
		String selectRaiseHead = "SELECT raiseHead FROM kinect "
				+ "WHERE time = " + time;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectRaiseHead);
			rs.next();
			raiseHead = Integer.parseInt(rs.getString(1));
			System.out.println(time.toString() + " raiseHead: " + raiseHead);
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
		return raiseHead;
	}
	
	public double selectBodyStraighten(String time) { 
		connect();
		this.time = time;
		String selectBodyStraighten = "SELECT bodyStraighten FROM kinect "
				+ "WHERE time = " + time;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectBodyStraighten);
			rs.next();
			bodyStraighten = rs.getDouble(1);
			System.out.println(time.toString() + " bodyStraighten: " + rs.getDouble(1));
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
		return bodyStraighten;
	}
	
	public double selectLeftArms(String time) { 
		connect();
		this.time = time;
		String selectLeftArms = "SELECT bodyStraighten FROM kinect "
				+ "WHERE time = " + time;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectLeftArms);
			rs.next();
			leftArms = rs.getDouble(1);
			System.out.println(time.toString() + " LeftArms: " + rs.getDouble(1));
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
		return leftArms;
	}
	
	public double selectRightArms(String time) { 
		connect();
		this.time = time;
		String selectRightArms = "SELECT rightArms FROM kinect "
				+ "WHERE time = " + time;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectRightArms);
			rs.next();
			rightArms = rs.getDouble(1);
			System.out.println(time.toString() + " rightArms: " + rs.getDouble(1));
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
		return rightArms;
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
		}
	}
	
//	public static void main(String[] args) {
//		DataBase db = new DataBase();
//		db.delete("123");
//		db.delete(null);
//		db.update("123", 0, 1, 0, 0);
//		System.out.println();
//		db.insert("123", 0, 1, 0, 0);
//		db.close();
//	}
	
}
