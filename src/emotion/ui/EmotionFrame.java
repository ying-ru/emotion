package emotion.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import emotion.padModel.Outcome;

public class EmotionFrame extends CopyOfSpatial {
	private OutcomePanel outcome;
	private LoadingPanel loading;
	private TrackingPanel tracking;
	private JLabel background;
	private Thread updateOutcome;
	private Outcome value;
	
	public EmotionFrame(String s) {
		super(s);
		initJPanel();
		initBackground();
		initBound();
		revalidate();
		repaint();
		updateOutcome();
		getTrackingPanel().getTrack().addObserver(getTrackingPanel().getTrackerPanel3D().getSkelsManager());
		getTrackingPanel().getTrack().addObserver(getTrackingPanel().getCopyOfMindStreamSystemTray());
		getTrackingPanel().getTrackerPanel3D().getSkelsManager().addObserver(getTrackingPanel().getTrack());
		getTrackingPanel().getCopyOfMindStreamSystemTray().addObserver(getTrackingPanel().getTrack());
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
		outcome = new OutcomePanel(getWidth() / 10,
				getHeight() / 10, getWidth()*4/5,
				getHeight()*7/10, 0);
		outcome.setVisible(false);
		add(outcome);
		
		tracking = new TrackingPanel(getWidth() / 18,
		getHeight() / 15, getWidth()*9/10,
		getHeight()*8/10);
//		tracking.setBorder(javax.swing.BorderFactory.createTitledBorder("Spatial Info"));
		
//		tracking.setVisible(false);
		add(tracking);
		
	}

	private void initBound() {
		background.setBounds(0, 0, this.getWidth(), this.getHeight());
	}
	
	// init Component end //
	
	// API//
	public TrackingPanel getTrackingPanel() {
		return tracking;
	}
	// API end //
	
	private void updateOutcome() {
		updateOutcome = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(100);
						// update outcome start
						if (tracking.isSaveOver()) {
							tracking.setisSaveOver();
							value = new Outcome();
							outcome.setValue(value.getOutcome());
							
							tracking.setVisible(false);
							outcome.setVisible(true);
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
