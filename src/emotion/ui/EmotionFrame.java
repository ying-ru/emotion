package emotion.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import emotion.padModel.Outcome;

public class EmotionFrame extends SpatialFrame {
	private static final long serialVersionUID = 1L;
	private OutcomePanel outcome;
	private ButtonPanel buttonPanel;
	private KinectPanel kinectPanel;
	private BrainwavePanel brainwavePanel;
	private static SensorStatus sensorStatus;
	private JLabel background;
	private JTabbedPane tp;
	private Thread updateOutcome;
	private Outcome value;
	private AccelerometerBarChartPanel barChartpanel;
	private FirstPanel fp;
	
	public EmotionFrame(String s) {
		super(s);
		
		
		initJPanel();
		initTab();
		initBackground();
		initBound();
		
		revalidate();
		repaint();
		updateOutcome();
		
		
		
//		fp.sensorStatus.getTrack().addObserver(fp.kinectPanel.getKinect().getSkelsManager());
		fp.sensorStatus.getTrack().addObserver(fp.brainwavePanel.getBrainwave());
//		fp.kinectPanel.getKinect().getSkelsManager().addObserver(fp.sensorStatus.getTrack());
		fp.brainwavePanel.getBrainwave().addObserver(fp.sensorStatus.getTrack());
		getSpatialDataListener().addObserver(fp.sensorStatus.getTrack());
		
		
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
//		outcome = new OutcomePanel((int)(getWidth() / 1.7), getHeight()*15/40, 
//				getWidth()*19/40, 480, 0);
//		
//		barChartpanel = new AccelerometerBarChartPanel(spatialData_listener);		
//		barChartpanel.setPreferredSize(new Dimension(350, 270));
//		barChartpanel.setBounds((int)(getWidth() / 1.4), getHeight() / 50, 350, 250);
//		cp.add(barChartpanel);
//		kinectPanel = new KinectPanel(getWidth() * 10 / 40, getHeight() * 7 / 20, 512, 512);
//		brainwavePanel = new BrainwavePanel(getWidth() / 4, getHeight() / 50, 700, 250);
//		buttonPanel = new ButtonPanel(getWidth() / 50, getHeight() / 28, getWidth() / 5, 250, 
//				brainwavePanel.getBrainwave(), kinectPanel.getKinect(), getSpatialDataListener());
//		sensorStatus = new SensorStatus(getWidth() / 50, getHeight() * 7 / 20, getWidth() / 5, 512);
//		cp.add(outcome);
//		cp.add(kinectPanel);
//		cp.add(brainwavePanel);
//		cp.add(buttonPanel);
//		cp.add(sensorStatus);
		fp = new FirstPanel(0, 0, this.getWidth(), this.getHeight(), getSpatialDataListener());
		
		
	}

	private void initBound() {
		background.setBounds(0, 0, this.getWidth(), this.getHeight());
	}
	
	private void initTab() {
		JTabbedPane tp = new JTabbedPane();
		tp.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 20));
		tp.addTab("主畫面", fp);
		tp.setSize(getWidth(), getHeight());
		tp.setLocation(0, 0);
		add(tp);

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
						if (fp.sensorStatus.isSaveOver()) {
							fp.sensorStatus.setisSaveOver();
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
