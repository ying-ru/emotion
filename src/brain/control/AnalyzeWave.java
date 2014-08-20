package brain.control;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;

public class AnalyzeWave {
	int lowAlphaMax, highAlphaMax, lowBetaMax, highBetaMax, lowGammaMax, highGammaMax;
	int lowAlphaMin, highAlphaMin, lowBetaMin, highBetaMin, lowGammaMin, highGammaMin;
	FileReader fr;
	
	public AnalyzeWave() {
		// TODO Auto-generated constructor stub
		
	}
	
	public void readMaxMin() throws IOException {
//		FileReader fr = new FileReader("C:\\Users\\banbi\\Desktop\\mindstream.csv");
//		FileReader fr = new FileReader("C:\\Users\\Sebastian\\Desktop\\mindstream -ROSE媽玩CS.csv");
		fr = new FileReader("src/file/mindstream.csv");

		BufferedReader br = new BufferedReader(fr);
		String readLine;
		
		lowAlphaMin = 10000;
		highAlphaMin = 10000;
		lowBetaMin = 10000;
		highBetaMin = 10000;
		lowGammaMin = 10000;
		highGammaMin = 10000;
		lowAlphaMax = 0;
		highAlphaMax = 0;
		lowBetaMax = 0;
		highBetaMax = 0;
		lowGammaMax = 0;
		highGammaMax = 0;
		
		while (br.ready()) {
//			System.out.println(br.readLine());
			readLine = br.readLine();
			String[] tokens = readLine.split(",");
			for (String token:tokens) {
//				System.out.println(token);
				if (lowAlphaMin > Integer.parseInt(tokens[1])) {
					lowAlphaMin = Integer.parseInt(tokens[1]);
				}
				if (highAlphaMin > Integer.parseInt(tokens[2])) {
					highAlphaMin = Integer.parseInt(tokens[2]);
				}
				if (lowBetaMin > Integer.parseInt(tokens[3])) {
					lowBetaMin = Integer.parseInt(tokens[3]);
				}
				if (highBetaMin > Integer.parseInt(tokens[4])) {
					highBetaMin = Integer.parseInt(tokens[4]);
				}
				if (lowGammaMin > Integer.parseInt(tokens[5])) {
					lowGammaMin = Integer.parseInt(tokens[5]);
				}
				if (highGammaMin > Integer.parseInt(tokens[6])) {
					highGammaMin = Integer.parseInt(tokens[6]);
				}
				
				
				
				if (lowAlphaMax < Integer.parseInt(tokens[1])) {
					lowAlphaMax = Integer.parseInt(tokens[1]);
				}
				if (highAlphaMax < Integer.parseInt(tokens[2])) {
					highAlphaMax = Integer.parseInt(tokens[2]);
				}
				if (lowBetaMax < Integer.parseInt(tokens[3])) {
					lowBetaMax = Integer.parseInt(tokens[3]);
				}
				if (highBetaMax < Integer.parseInt(tokens[4])) {
					highBetaMax = Integer.parseInt(tokens[4]);
				}
				if (lowGammaMax < Integer.parseInt(tokens[5])) {
					lowGammaMax = Integer.parseInt(tokens[5]);
				}
				if (highGammaMax < Integer.parseInt(tokens[6])) {
					highGammaMax = Integer.parseInt(tokens[6]);
				}
			}
		}
		

		fr.close();
	}
	
	public double readFormat(String s, int i, int j) throws IOException {
//		FileReader fr = new FileReader("C:\\Users\\banbi\\Desktop\\mindstream.csv");
//		FileReader fr = new FileReader("C:\\Users\\Sebastian\\Desktop\\mindstream -ROSE媽玩CS.csv");
		FileReader fr = new FileReader("src/file/mindstream.csv");
		
		BufferedReader br = new BufferedReader(fr);
		String readLine;
		int p1, p2, p3, p4, p5, p6, p7, p8, p9, p10;
		double pTotal;
		p1 = 0;
		p2 = 0;
		p3 = 0;
		p4 = 0;
		p5 = 0;
		p6 = 0;
		p7 = 0;
		p8 = 0;
		p9 = 0;
		p10 = 0;
		pTotal = 0.0;
		
		while (br.ready()) {
//			System.out.println(br.readLine());
			readLine = br.readLine();
			String[] tokens = readLine.split(",");
			
//			System.out.println(token);
			String str;
			
			double p = Integer.parseInt(tokens[j]) * 100.0 / i * 1 ;
//			System.out.println(Integer.parseInt(tokens[j])/(double)i);
			if (p <= 10) {
				p1++;
			} else if (p <= 20) {
				p2++;
			} else if (p <= 30) {
				p3++;
			} else if (p <= 40) {
				p4++;
			} else if (p <= 50) {
				p5++;
			} else if (p <= 60) {
				p6++;
			} else if (p <= 70) {
				p7++;
			} else if (p <= 80) {
				p8++;
			} else if (p <= 90) {
				p9++;
			} else if (p <= 100){
				p10++;
			} else {
				p = 100;
				p10++;
			}
			pTotal = pTotal + p;
		}
		System.out.println(s + ": \n0~10%: " + p1 + "\n" + "10~20%: " + p2 + "\n"
				+ "20~30%: " + p3 + "\n" + "30~40%: " + p4 + "\n"
				+ "40~50%: " + p5 + "\n" + "50~60%: " + p6 + "\n"
				+ "60~70%: " + p7 + "\n" + "70~80%: " + p8 + "\n"
				+ "80~90%: " + p9 + "\n" + "90~100%: " + p10 + "\n");

		fr.close();
		return (pTotal / 30.0 * 2 / 100.0) - 1;
	}
	
	public String readBrainwave() throws IOException {
		String bw;
		readMaxMin();
		bw = readFormat("lowAlpha", lowAlphaMax, 1) + ","
				 + readFormat("highAlpha", highAlphaMax, 2) + ","
				 + readFormat("lowBeta", lowBetaMax, 3) + ","
				 + readFormat("highBeta", highBetaMax, 4) + ","
				 + readFormat("lowGamma", lowGammaMax, 5) + ","
				 + readFormat("highGamma", highGammaMax, 6);
		return bw;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		AnalyzeWave f = new AnalyzeWave();
//		f.readMaxMin();
//		System.out.println("lowAlphaMax: " + f.lowAlphaMax + "\n" + "highAlphaMax: " + f.highAlphaMax + "\n"
//				+ "lowBetaMax: " + f.lowBetaMax + "\n" + "highBetaMax: " + f.highBetaMax + "\n"
//				+ "lowGammaMax: " + f.lowGammaMax + "\n" + "highGammaMax: " + f.highGammaMax + "\n"
//				+ "lowAlphaMin: " + f.lowAlphaMin + "\n" + "highAlphaMin: " + f.highAlphaMin + "\n"
//				+ "lowBetaMin: " + f.lowBetaMin + "\n" + "highBetaMin: " + f.highBetaMin + "\n"
//				+ "lowGammaMin: " + f.lowGammaMin + "\n" + "highGammaMin: " + f.highGammaMin + "\n");
//		
//		System.out.println(f.readFormat("lowAlpha", f.lowAlphaMax, 0));
//		System.out.println(f.readFormat("highAlpha", f.highAlphaMax, 1));
//		System.out.println(f.readFormat("lowBeta", f.lowBetaMax, 2));
//		System.out.println(f.readFormat("highBeta", f.highBetaMax, 3));
//		System.out.println(f.readFormat("lowGamma", f.lowGammaMax, 4));
//		System.out.println(f.readFormat("highGamma", f.highGammaMax, 5));
		
		System.out.println(f.readBrainwave());
	}

}
