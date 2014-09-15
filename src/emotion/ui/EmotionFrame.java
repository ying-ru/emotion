package emotion.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

import emotion.padModel.Outcome;

public class EmotionFrame extends SpatialFrame {
	private static final long serialVersionUID = 1L;
	private OutcomePanel outcome;
	private ButtonPanel buttonPanel;
	private KinectPanel kinectPanel;
	private BrainwavePanel brainwavePanel;
	private static SensorStatus sensorStatus;
	private JLabel background;
	private Thread updateOutcome;
	private Outcome value;
	private AccelerometerBarChartPanel barChartpanel;
	
	public EmotionFrame(String s) {
		super(s);
		initJPanel();
		initBackground();
		initBound();
		revalidate();
		repaint();
		updateOutcome();
		sensorStatus.getTrack().addObserver(kinectPanel.getKinect().getSkelsManager());
		sensorStatus.getTrack().addObserver(brainwavePanel.getBrainwave());
		kinectPanel.getKinect().getSkelsManager().addObserver(sensorStatus.getTrack());
		brainwavePanel.getBrainwave().addObserver(sensorStatus.getTrack());
		getSpatialDataListener().addObserver(sensorStatus.getTrack());
	}

	// init Component //

	private void initBackground() { // 加入背景圖片
		background = new JLabel();
		background.setBackground(Color.black);
		background.setOpaque(true);
		add(background);
	}
	
	private void initJPanel() {
//		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Spatial Info"));
//        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
		outcome = new OutcomePanel((int)(getWidth() / 1.7), getHeight()*15/40, 
				getWidth()*19/40, 480, 0);
		
		barChartpanel = new AccelerometerBarChartPanel(spatialData_listener);		
		barChartpanel.setPreferredSize(new Dimension(350, 270));
		barChartpanel.setBounds((int)(getWidth() / 1.4), getHeight() / 50, 350, 250);
		add(barChartpanel);
		kinectPanel = new KinectPanel(getWidth() * 10 / 40, getHeight() * 7 / 20, 512, 512);
		brainwavePanel = new BrainwavePanel(getWidth() / 4, getHeight() / 50, 700, 250);
		buttonPanel = new ButtonPanel(getWidth() / 50, getHeight() / 28, getWidth() / 5, 250, 
				brainwavePanel.getBrainwave(), kinectPanel.getKinect(), getSpatialDataListener());
		sensorStatus = new SensorStatus(getWidth() / 50, getHeight() * 7 / 20, getWidth() / 5, 512);
		add(outcome);
		add(kinectPanel);
		add(brainwavePanel);
		add(buttonPanel);
		add(sensorStatus);
	}

	private void initBound() {
		background.setBounds(0, 0, this.getWidth(), this.getHeight());
	}
	
	// init Component end //
	
	// API//
	
	// API end //
	
	private void updateOutcome() {
		updateOutcome = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(100);
						// update outcome start
						if (sensorStatus.isSaveOver()) {
							sensorStatus.setisSaveOver();
							value = new Outcome();
							outcome.setValue(value.getOutcome());
						}
						// update outcome end
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		updateOutcome.start();
	}
}
