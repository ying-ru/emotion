package skeletons3D;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import emotion.jdbc.DataBase;

public class File {
	private double dataNumber = 10.0;
	
	public void read() throws IOException {
//		FileReader fr = new FileReader("C:\\Users\\banbi\\Desktop\\kinect.csv");
//		FileReader fr = new FileReader("C:\\Users\\Sebastian\\Desktop\\kinect.csv");
		FileReader fr = new FileReader("src/file/kinect.csv");

		DataBase db = new DataBase();
		BufferedReader br = new BufferedReader(fr);
		String readLine;
		while (br.ready()) {
			readLine = br.readLine();
			db.insert(readLine);
			System.out.println(readLine);
		}
		fr.close();
	}
	
	public String readKinect() throws IOException {
//		FileReader fr = new FileReader("C:\\Users\\banbi\\Desktop\\kinect.csv");
//		FileReader fr = new FileReader("C:\\Users\\Sebastian\\Desktop\\kinect.csv");
		FileReader fr = new FileReader("src/file/kinect.csv");

//		DataBase db = new DataBase();
		BufferedReader br = new BufferedReader(fr);
		String readLine;
		double a = 0, b = 0, c = 0, d = 0;
		while (br.ready()) {
			readLine = br.readLine();
//			db.insert(readLine);
			String[] tokens = readLine.split(",");
			a = a + Double.parseDouble(tokens[1]);
			b = b + Double.parseDouble(tokens[2]);
			c = c + Double.parseDouble(tokens[3]);
			d = d + Double.parseDouble(tokens[4]);
			
		}
		fr.close();
		a = a / dataNumber;
		b = b / dataNumber;
		c = c / dataNumber;
		d = d / dataNumber;
		System.out.println("kinect: " + a + "," + b + "," + c + "," + d);
		return a + "," + b + "," + c + "," + d;
	}
	
	public double readActivity() throws IOException {
//		FileReader fr = new FileReader("C:\\Users\\banbi\\Desktop\\kinect.csv");
//		FileReader fr = new FileReader("C:\\Users\\Sebastian\\Desktop\\kinect.csv");
		FileReader fr = new FileReader("src/file/activity.csv");

//		DataBase db = new DataBase();
		BufferedReader br = new BufferedReader(fr);
		String readLine;
		double a = 0, b = 0, c = 0;
		
		while (br.ready()) {
			readLine = br.readLine();
//			db.insert(readLine);
			String[] tokens = readLine.split(",");
			a = a + Double.parseDouble(tokens[1]);
			b = b + Double.parseDouble(tokens[2]);
			c = c + Double.parseDouble(tokens[3]);
			
		}
		fr.close();
		a = a / dataNumber;
		b = b / dataNumber;
		c = c / dataNumber;
		System.out.println("activity: " + (a + b + c) / 3.0 / 1000.0);
		return (a + b + c) / 3.0 / 1000.0;
	}
	
//	public static void main(String[] argv) throws IOException {
//		File f = new File();
//		f.read();
//	}
}
