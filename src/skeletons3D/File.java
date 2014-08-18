package skeletons3D;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import jdbc.DataBase;

public class File {
	
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
			System.out.println(readLine);
		}
		fr.close();
		a = a / 30.0;
		b = b / 30.0;
		c = c / 30.0;
		d = d / 30.0;
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
			a = a + Double.parseDouble(tokens[0]);
			b = b + Double.parseDouble(tokens[1]);
			c = c + Double.parseDouble(tokens[2]);
			System.out.println(readLine);
		}
		fr.close();
		a = a / 30.0;
		b = b / 30.0;
		c = c / 30.0;
		return (a + b + c) / 30.0;
	}
	
//	public static void main(String[] argv) throws IOException {
//		File f = new File();
//		f.read();
//	}
}
