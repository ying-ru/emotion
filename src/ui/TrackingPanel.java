package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import skeletons3D.SkelsManager;
import skeletons3D.TrackerPanel3D;

import com.ericblue.mindstream.systemtray.CopyOfMindStreamSystemTray;
import com.ericblue.mindstream.window.DebugWindow;

public class TrackingPanel extends JPanel {
	
	private JButton connect, save, display;
	private JTextArea connectStatus;
	private CopyOfMindStreamSystemTray brainwave;
	private DebugWindow debug;
	private TrackerPanel3D tp3D;
	private Track track;
	private boolean isSaveOver;
	
	public TrackingPanel(int locationX, int locationY, int width, int height) {
		// TODO Auto-generated constructor stub
		
		setSize(width, height);
		setLocation(locationX, locationY);
		setLayout(null);
		setOpaque(false);
		initJButton();
		setComponentFont();
		initJPanel();
		initJLabel();
		brainwave = new CopyOfMindStreamSystemTray(debug);
		track = new Track();
		isSaveOver = false;
	}
	
	private void setComponentFont() {
//		connect.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 40));
//		save.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 40));
//		connectStatus.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 20));
	}
	
	private void initJButton() {
		connect = new JButton("腦波連線");
		connect.setSize(getWidth() / 15, getHeight() / 15);
		connect.setLocation(0, 0);
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				brainwave.actionConnected();
				brainwave.actionDebugWindow();
			}
		});
		add(connect);
		
//		display = new JButton("顯示");
//		display.setSize(getWidth() / 15, getHeight() / 15);
//		display.setLocation(0, getHeight() / 10);
//		display.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				a.actionDebugWindow();
//			}
//		});
//		add(display);
		
		save = new JButton("存檔");
		save.setSize(getWidth() / 15, getHeight() / 15);
		save.setLocation(0, getHeight() / 5);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				brainwave.actionSaveFile();
				tp3D.getSkelsManager().write();
				
			}
		});
		add(save);
	}
	
	private void initJLabel() {
		connectStatus = new JTextArea("腦波儀：尋找中\n" + "體感偵測器：尋找中\n");
		connectStatus.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(connectStatus);
		scrollPane.setPreferredSize(new Dimension(512, 512));
		scrollPane.setBounds(0, (int)(getHeight() / 2.5), getWidth() / 12, 512);
		add(scrollPane);
		
	}
	
	private void initJPanel() {
		debug = new DebugWindow((int)(getWidth() / 1.118), (int)(getHeight() / 3.1));
		debug.setBounds(getWidth() / 10, 0, getWidth(), getHeight() / 3);
//		debug.getTextArea().append("123\n");
//		debug.setVisible(false);
		add(debug);
		
		tp3D = new TrackerPanel3D();
		tp3D.setBounds(getWidth() / 10, (int)(getHeight() / 2.5), 512, 512);
//		debug.getTextArea().append("123\n");
//		debug.setVisible(false);
		add(tp3D, BorderLayout.CENTER);
	}
	
	public void setStatus(String str) {
		connectStatus.replaceRange(str, 0, 18);
	}
	
	public TrackerPanel3D getTrackerPanel3D() {
		return tp3D;
	}
	
	public Track getTrack() {
		return track;
	}
	
	public CopyOfMindStreamSystemTray getCopyOfMindStreamSystemTray() {
		return brainwave;
	}
	
	public boolean isSaveOver() {
		return isSaveOver;
	}
	
	public void setisSaveOver() {
		isSaveOver = false;
	}
	
	class Track extends Observable implements Observer {
		private String brainwave = "腦波儀：尋找中\n", kinect = "體感偵測器：尋找中\n";
		private boolean brainTrack = false, kinectTrack = false;
		private boolean brainOk = false, kinectOk = false;
		@Override
		public void update(Observable o, Object arg) {
			// TODO Auto-generated method stub
			if (o instanceof CopyOfMindStreamSystemTray) {
				if (arg instanceof String && arg.equals("brain")) {
					brainwave = "腦波儀：追蹤中\n";
					setStatus(brainwave + kinect);
					brainTrack = true;
				}
			}
			
			if (o instanceof SkelsManager) {
				if (arg instanceof String && arg.equals("kinect")) {
					kinect = "體感偵測器：追蹤中\n";
					setStatus(brainwave + kinect);
					kinectTrack = true;
				}
			}
			
			if (brainTrack && kinectTrack) {
				connectStatus.append("可存檔");
//				setChanged();
//				notifyObservers();
			}
			
			if (o instanceof CopyOfMindStreamSystemTray) {
				if (arg instanceof String && arg.equals("ok")) {
					brainOk = true;
				}
			}
			
			if (o instanceof SkelsManager) {
				if (arg instanceof String && arg.equals("ok")) {
					kinectOk = true;
				}
			}
			
			if (brainOk && kinectOk) {
				isSaveOver = true;
//				setChanged();
//				notifyObservers();
			}
		}
	}
}
