package padModel;

import java.io.IOException;

import com.ericblue.mindstream.systemtray.AnalyzeWave;

import skeletons3D.File;
import jdbc.DataBase;
import jdbc.JDBC;

public class CalculatePad {
	private DataBase db;
	private JDBC jdbc;
	private File file;
	private AnalyzeWave f;
	private double raiseHead, bodyStraighten, leftArms, rightArms;
	private double lowAlpha, highAlpha, lowBeta, highBeta, lowGamma, highGamma;
	private double activity;
	
	public CalculatePad() {
		db = new DataBase();
		jdbc = new JDBC();
		file = new File();
		f = new AnalyzeWave();
		try {
			getKinect();
			getBrainwave();
			getAccelerometer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getKinect() throws IOException {
		String k;
		k = file.readKinect();
		String[] token = k.split(",");
		raiseHead = Double.parseDouble(token[0]);
		bodyStraighten = Double.parseDouble(token[1]);
		leftArms = Double.parseDouble(token[2]);
		rightArms = Double.parseDouble(token[3]);
	}
	
	private void getBrainwave() throws IOException {
		String b;
		b = f.readBrainwave();
		String[] token = b.split(",");
		lowAlpha = Double.parseDouble(token[0]);
		highAlpha = Double.parseDouble(token[1]);
		lowBeta = Double.parseDouble(token[2]);
		highBeta = Double.parseDouble(token[3]);
		lowGamma = Double.parseDouble(token[4]);
		highGamma = Double.parseDouble(token[5]);
	}
	
	private void getAccelerometer() throws IOException {
//		activity = 0;
		double k;
		k = file.readActivity();
		activity = k;
	}
	
//	愉悅度↑：活動量↑、抬頭、身體直立、上臂不向前、Alpha
//	活動量 25% (加速器)
//	Alpha 20% (腦波)
//	抬頭 20% (體感偵測器)
//	身體直立 20% (體感偵測器)
//	上臂不向前 15% (體感偵測器)
	public double getValueP() {
		//db select data and calculate them
		double p;
		p = activity * 0.25 + highAlpha * 0.1 + lowAlpha * 0.1 
				+ raiseHead * 0.2 + bodyStraighten * 0.2
				+ rightArms * 0.075 + leftArms * 0.075;
		return p;
	}
	
//	激動度↑：活動量↑、Alpha波↓、Beta波↑
//	活動量 35% (加速器)
//	Alpha波 30% (腦波)
//	Beta波 35% (腦波)
	public double getValueA() {
		double a;
		a = activity * 0.35 - highAlpha * 0.15 - lowAlpha * 0.15 
				+ highBeta * 0.175 + lowBeta * 0.175;
		return a;
	}
	
//	支配度↑：Gamma波↑
//	Gamma波 100% (腦波)
	public double getValueD() {
		double d;
		d = highGamma * 0.5 + lowGamma * 0.5;
		return d;
	}
}
