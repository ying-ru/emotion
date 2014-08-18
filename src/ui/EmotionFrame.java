package ui;

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

import padModel.Outcome;

public class EmotionFrame extends MainFrame {
	private OutcomePanel outcome;
	private LoadingPanel loading;
	private TrackingPanel tracking;
	private JLabel background, forWhoToPlay;
	private JButton leaveBtn;
	private ImageIcon backgroundPhoto;
	private String userToken;
	private Thread playTooLong;
	private Thread updateOutcome;
	private Outcome value;
	private boolean isRemind = false;
	private static String[] arg;
	
	public EmotionFrame() {
		initJPanel();
		initJButton();
		initJLabel();
		initBackground();
		initBound();
		setComponentFont();
		revalidate();
		repaint();
		updateOutcome();
	}

	// init Component //

	private void setComponentFont() {
//		leaveBtn.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 40));
//		forWhoToPlay
//				.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 20));
	}

	private void initJButton() {
//		leaveBtn = new JButton("離開");
//		leaveBtn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					if (server.getRoom() != -1) {
//						server.s.exit(server.getRoom(), userToken);
//					}
//				} catch (RemoteException e1) {
//					e1.printStackTrace();
//				}
//				System.exit(0);
//			}
//		});
//		add(leaveBtn);
	}

	private void initJLabel() {
//		forWhoToPlay = new JLabel("輪到你了");
//		forWhoToPlay.setForeground(Color.red);
//		add(forWhoToPlay);
	}

	private void initBackground() { // 加入背景圖片
		background = new JLabel();
		background.setBackground(Color.darkGray);
		background.setOpaque(true);
		add(background);
	}
	
	private void initJPanel() {
		outcome = new OutcomePanel(getWidth() / 10,
				getHeight() / 10, getWidth()*4/5,
				getHeight()*7/10, 0);
		outcome.setVisible(false);
		add(outcome);
		outcome.setValue(75);
//		
		tracking = new TrackingPanel(getWidth() / 18,
		getHeight() / 15, getWidth()*9/10,
		getHeight()*8/10, arg);
//		tracking.setVisible(false);
		add(tracking);
		
	}

	private void initBound() {
		background.setBounds(0, 0, this.getWidth(), this.getHeight());
//		forWhoToPlay.setBounds(getWidth() - getWidth() / 20 * 6,
//				getHeight() / 90 * 14, getWidth() / 4, getHeight() / 90 * 8);
//		leaveBtn.setBounds(getWidth() - getWidth() / 15 * 2,
//				getHeight() / 90 * 14, getWidth() / 10, getHeight() / 90 * 8);
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
	
	public static void main(String args[]) {
		EmotionFrame e = new EmotionFrame();
		arg = args;
		e.tracking.getTrack().addObserver(e.tracking.getTrackerPanel3D().getSkelsManager());
		e.tracking.getTrack().addObserver(e.tracking.getCopyOfMindStreamSystemTray());
		e.tracking.getTrackerPanel3D().getSkelsManager().addObserver(e.tracking.getTrack());
		e.tracking.getCopyOfMindStreamSystemTray().addObserver(e.tracking.getTrack());
		e.tracking.getCopyOfSpatial().getSpatialSpatialDataListener().addObserver(e.tracking.getTrack());
	}
}
