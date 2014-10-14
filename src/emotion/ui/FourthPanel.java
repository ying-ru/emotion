package emotion.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import emotion.padModel.Outcome;

public class FourthPanel extends JPanel {
	
	private OutcomePanel outcome;
	private ButtonPanel buttonPanel;
	KinectPanel kinectPanel;
	BrainwavePanel brainwavePanel;
	SensorStatus sensorStatus;
	private JLabel background;
	private JTabbedPane tp;
	private Thread updateOutcome;
	private Outcome value;
	private AccelerometerBarChartPanel barChartpanel;
	private JTextArea discription;
	
	public FourthPanel(int width, int height) {
		setSize(width, height);
		setLayout(null);
		setOpaque(false);
		initJLabel();
		initBackground();
		initBound();
	}
	
	private void initBackground() { // 加入背景圖片
		background = new JLabel();
		background.setBackground(Color.black);
		background.setOpaque(true);
		add(background);
	}
	
	private void initBound() {
		background.setBounds(0, 0, this.getWidth(), this.getHeight());
	}
	
	private void initJLabel() {
		String temp;
		temp = " PAD計算方式：\n"
				+ " 愉悅度(P)↑：活動量↑、抬頭、身體直立、上臂不向前、Alpha↑\n"
				+ "     活動量 25% (加速器)、Alpha 20% (腦波)、抬頭 20% (體感偵測器)\n"
				+ "     身體直立 20% (體感偵測器)、上臂不向前 15% (體感偵測器)\n\n"
				+ " 激動度(A)↑：活動量↑、Alpha波↓、Beta波↑\n"
				+ "     活動量 35% (加速器)Alpha波 30% (腦波)Beta波 35% (腦波)\n\n"
				+ " 支配度(D)↑：Gamma波↑\n"
				+ "     Gamma波 100% (腦波)\n"
				+ " ※支配度對於情緒指標的影響力較低\n";
		
		discription = new JTextArea(temp);
		discription.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 25));
		discription.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(discription);
		scrollPane.setPreferredSize(new Dimension(512, 512));
		scrollPane.setBounds(0, 0, getWidth(), getHeight());
		add(scrollPane);
	}
}
