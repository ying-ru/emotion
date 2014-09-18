package emotion.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
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
	
	public void insertKinect(String data) {
		connect();
		String insert = "INSERT INTO kinect VALUES (" + data + ")";
		try {
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(insert);
			System.out.println("kinect: " + result + " data was insert");
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
	}
	
	public void insertAccelerometer(String data) {
		connect();
		String insert = "INSERT INTO accelerometer VALUES (" + data + ")";
		try {
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(insert);
			System.out.println("accelerometer: " + result + " data was insert");
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
	}
	
	public void insertBrainwave(String data) {
		connect();
		String insert = "INSERT INTO brainwave VALUES (" + data + ")";
		try {
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(insert);
			System.out.println("brainwave: " + result + " data was insert");
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
	}

	public void insertPAD(String data) {
		connect();
		String insert = "INSERT INTO pad VALUES (" + data + ")";
		try {
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(insert);
			System.out.println("pad: " + result + " data was insert");
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
	}
	
	public void updateOrder() {
		int order = selectOrder() + 1;
		connect();
		String update = "UPDATE emotion.order SET number = " + order;
		try {
			stmt = connection.createStatement();
			int result = stmt.executeUpdate(update);
			System.out.println("Order: " + result + " data was update");
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
	}
//	
//	public void deleteKinect(String time) {
//		connect();
//		this.time = time;
//		String delete = "DELETE FROM kinect "
//				+ "WHERE time = " + time;
//		try {
//			stmt = connection.createStatement();
//			int result = stmt.executeUpdate(delete);
//			System.out.println(result + " data was delete");
//			close();
//		} catch (SQLException e) {
//			System.out.println("CreateDB Exception :" + e.toString());
//		}
//	}
	
	public int selectOrder() { 
		connect();
		String selectOrder = "SELECT number FROM emotion.order";
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectOrder);
			rs.next();
			int order = rs.getInt(1);
			close();
			return order;
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
			return -999;
		}
	}
	
	public double selectRaiseHead(int order) {
		connect();
		String selectRaiseHead = "SELECT raiseHead FROM kinect "
				+ "WHERE order = " + order;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectRaiseHead);
			rs.next();
			raiseHead = rs.getDouble(1);
//			System.out.println(" raiseHead: " + raiseHead);
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
		return raiseHead;
	}
	
	public double selectBodyStraighten(int order) { 
		connect();
		String selectBodyStraighten = "SELECT bodyStraighten FROM kinect "
				+ "WHERE order = " + order;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectBodyStraighten);
			rs.next();
			bodyStraighten = rs.getDouble(1);
//			System.out.println(" bodyStraighten: " + rs.getDouble(1));
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
		return bodyStraighten;
	}
	
	public double selectLeftArms(int order) { 
		connect();
		String selectLeftArms = "SELECT bodyStraighten FROM kinect "
				+ "WHERE order = " + order;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectLeftArms);
			rs.next();
			leftArms = rs.getDouble(1);
//			System.out.println(" LeftArms: " + rs.getDouble(1));
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
		return leftArms;
	}
	
	public double selectRightArms(int order) { 
		connect();
		String selectRightArms = "SELECT rightArms FROM kinect "
				+ "WHERE order = " + order;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(selectRightArms);
			rs.next();
			rightArms = rs.getDouble(1);
//			System.out.println(" rightArms: " + rs.getDouble(1));
			close();
		} catch (SQLException e) {
			System.out.println("CreateDB Exception :" + e.toString());
		}
		return rightArms;
	}
	
	public int selectWave(String time, String wave) {
		int value = 0;
		connect();
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + wave + " FROM brainwave "
				+ "WHERE time = " + time);
			rs.next();
			value = rs.getInt(1);
			System.out.println(wave + ": " + rs.getInt(1));
			close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
		}
	}
	
	public static void main(String[] args) {
		DataBase db = new DataBase();
		db.updateOrder();
		System.out.println(db.selectOrder());
		db.close();
	}
	
}
