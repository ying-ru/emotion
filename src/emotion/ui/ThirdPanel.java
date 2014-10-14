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

public class ThirdPanel extends JPanel {
	
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
	
	public ThirdPanel(int width, int height) {
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
		temp = " 愉悅度(P)：未進行測量\n 激動度(A)：未進行測量\n 支配度(D)：未進行測量\n"
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

		
		discription = new JTextArea(temp);
		discription.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 25));
		discription.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(discription);
		scrollPane.setPreferredSize(new Dimension(512, 512));
		scrollPane.setBounds(0, 0, getWidth(), getHeight());
		add(scrollPane);
	}
}
