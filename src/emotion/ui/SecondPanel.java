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

public class SecondPanel extends JPanel {
	
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
	
	public SecondPanel(int width, int height) {
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
		temp = " 肢體動作分析：\n"
				+ " 抬頭：未進行測量\n"
				+ " 身體直立：未進行測量\n"
				+ " 左臂向前：未進行測量\n"
				+ " 右臂向前：未進行測量\n";
		
		
		discription = new JTextArea(temp);
		discription.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 25));
		discription.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(discription);
		scrollPane.setPreferredSize(new Dimension(512, 512));
		scrollPane.setBounds(0, 0, getWidth(), getHeight());
		add(scrollPane);
	}
}
