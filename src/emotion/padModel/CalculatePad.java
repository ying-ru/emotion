package emotion.padModel;

import java.io.IOException;

import emotion.jdbc.DataBase;
import emotion.jdbc.JDBC;
import brainwave.control.AnalyzeWave;
import skeletons3D.File;

public class CalculatePad {
	private DataBase db;
	private JDBC jdbc;
	private File file;
	private AnalyzeWave f;
	private double raiseHead, bodyStraighten, leftArms, rightArms;
	private double lowAlpha, highAlpha, lowBeta, highBeta, lowGamma, highGamma;
	private double activity;
	private double p, a, d;
	
	public CalculatePad() {
		db = new DataBase();
		jdbc = new JDBC();
		file = new File();
		f = new AnalyzeWave();
		try {
			setKinect();
			setBrainwave();
			setAccelerometer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setKinect() throws IOException {
		String k;
		k = file.readKinect();
		String[] token = k.split(",");
		raiseHead = Double.parseDouble(token[0]);
		bodyStraighten = Double.parseDouble(token[1]);
		leftArms = Double.parseDouble(token[2]);
		rightArms = Double.parseDouble(token[3]);
	}
	
	private void setBrainwave() throws IOException {
		String b;
		b = f.readBrainwave();
		System.out.println("brainwave: " + b);
		String[] token = b.split(",");
		lowAlpha = Double.parseDouble(token[0]);
		highAlpha = Double.parseDouble(token[1]);
		lowBeta = Double.parseDouble(token[2]);
		highBeta = Double.parseDouble(token[3]);
		lowGamma = Double.parseDouble(token[4]);
		highGamma = Double.parseDouble(token[5]);
	}
	
	private void setAccelerometer() throws IOException {
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
		p = activity * 0.25 + highAlpha * 0.1 + lowAlpha * 0.1 
				+ raiseHead * 0.2 + bodyStraighten * 0.2
				+ rightArms * 0.075 + leftArms * 0.075;
		System.out.println("P: " + p);
		return p;
	}
	
//	激動度↑：活動量↑、Alpha波↓、Beta波↑
//	活動量 35% (加速器)
//	Alpha波 30% (腦波)
//	Beta波 35% (腦波)
	public double getValueA() {
		a = activity * 0.35 - highAlpha * 0.15 - lowAlpha * 0.15 
				+ highBeta * 0.175 + lowBeta * 0.175;
		System.out.println("A: " + a);
		return a;
	}
	
//	支配度↑：Gamma波↑
//	Gamma波 100% (腦波)
	public double getValueD() {
		d = highGamma * 0.5 + lowGamma * 0.5;
		System.out.println("D: " + d);
		return d;
	}
	
	public String getKinectString() {
		return " ※肢體動作分析\n"
				+ " 抬頭：" + (int)((this.raiseHead+1)*50)
				+ "%\n 身體直立：" + (int)((this.bodyStraighten+1)*50)
				+ "%\n 左臂向前：" + (int)(100-(this.leftArms+1)*50)
				+ "%\n 右臂向前：" + (int)(100-(this.rightArms+1)*50) + "%\n";
	}
	
	public String getPadString() {
		return " 愉悅度(P)：" + p
				+ "\n 激動度(A)：" + a
				+ "\n 支配度(D)：" + d
				+ "\n"
				+ "※PAD的範圍皆介於 -1 ~ +1 之間，數值越大表示所代表的程度越大。\n\n"
				+ " 關於PAD情緒狀態模型：\n"
				+ " P為情緒愉悅度(Pleasure)\n"
				+ "     使用kinect偵測肢體動作配合Alpha波以及加速計偵測活動量進行處理。\n"
				+ " A為情緒激動度(Arousal)\n"
				+ "     激動程度和活動量最相關，因此使用加速計偵測活動量配合Alpha波、\n"
				+ "     Beta波進行處理。\n"
				+ " D為情緒支配度(Dominance)\n"
				+ "     Gamma波主要為精神上的認知，對於情緒的支配度也有關聯，\n"
				+ "     因此這部分主要由偵測Gamma波進行處理。\n";
	}
}
