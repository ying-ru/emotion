package emotion.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import accelerometer.listeners.SpatialSpatialDataListener;
import emotion.padModel.Outcome;

public class FirstPanel extends JPanel {

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
	
	SpatialSpatialDataListener spatialData_listener;
	
	public FirstPanel(int width, int height, SpatialSpatialDataListener spatialData_listener) {
		// TODO Auto-generated constructor stub
		this.spatialData_listener = spatialData_listener;
		setSize(width, height);
		setLayout(null);
		setOpaque(false);
		initJPanel();
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
	
	private void initJPanel() {
//		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Spatial Info"));
//        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
		outcome = new OutcomePanel((int)(getWidth() / 1.7), getHeight()*15/40, 
				getWidth()*19/40, getHeight()*9/20, 0);
		
		barChartpanel = new AccelerometerBarChartPanel(spatialData_listener);		
		barChartpanel.setPreferredSize(new Dimension(350, 270));
		barChartpanel.setBounds((int)(getWidth() / 1.37), getHeight() / 50, getWidth() / 4, getHeight() / 3);
		add(barChartpanel);
//		kinectPanel = new KinectPanel(getWidth() * 10 / 40, getHeight() * 15 / 40, 512, 512);
		brainwavePanel = new BrainwavePanel(getWidth() *9/ 40, getHeight() / 50, getWidth()*10/20, getHeight()/3);
//		buttonPanel = new ButtonPanel(getWidth() / 50, getHeight() / 28, getWidth() / 5, 250, 
//				brainwavePanel.getBrainwave(), kinectPanel.getKinect(), getSpatialDataListener());
		sensorStatus = new SensorStatus(getWidth() / 50, getHeight() * 15 / 40, getWidth() / 5, getHeight()*9/20);
		add(outcome);
//		add(kinectPanel);
		add(brainwavePanel);
//		add(buttonPanel);
		add(sensorStatus);
	}
	
	
}
