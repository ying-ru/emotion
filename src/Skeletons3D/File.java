package Skeletons3D;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class File {
	
	public void read() throws IOException {
		FileReader fr = new FileReader("C:\\Users\\banbi\\Desktop\\kinect.csv");
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
	
//	public static void main(String[] argv) throws IOException {
//		File f = new File();
//		f.read();
//	}
}
