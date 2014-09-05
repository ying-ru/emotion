package emotion.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import skeletons3D.SkelsManager;
import accelerometer.listeners.SpatialSpatialDataListener;
import brainwave.control.BrainwaveControl;

public class SensorStatus  extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea status;
	private Track track;
	private boolean isSaveOver;
	
	public SensorStatus(int locationX, int locationY, int width, int height) {
		// TODO Auto-generated constructor stub
		setSize(width, height);
		setLocation(locationX, locationY);
		setLayout(null);
		setOpaque(false);
		initJLabel();
		track = new Track();
		isSaveOver = false;
	}
	
	private void initJLabel() {
		status = new JTextArea(" 腦波儀：\n 尋找中\n" + "--------------------------\n" + " 體感偵測器：\n 尋找中\n");
		status.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 20));
		status.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(status);
		scrollPane.setPreferredSize(new Dimension(512, 512));
		scrollPane.setBounds(0, 0, getWidth(), getHeight());
		add(scrollPane);
		
	}
	
	public Track getTrack() {
		return track;
	}
	
	public boolean isSaveOver() {
		return isSaveOver;
	}
	
	public void setisSaveOver() {
		isSaveOver = false;
	}
	
	public void setStatus(String str) {
		status.setText(str);
	}
	
	public class Track extends Observable implements Observer {
		private String brainwave = " 腦波儀：\n 尋找中\n", kinect = " 體感偵測器：\n 尋找中\n";
		private boolean brainTrack = false, kinectTrack = false;
		private boolean brainOk = false, kinectOk = false, actOk = false;
		@Override
		public void update(Observable o, Object arg) {
			// TODO Auto-generated method stub
			if (o instanceof BrainwaveControl) {
				if (arg instanceof String && arg.equals("brain")) {
					brainwave = " 腦波儀：\n 追蹤中\n";
					setStatus(brainwave + "--------------------------\n" + kinect);
					brainTrack = true;
					if (brainTrack && kinectTrack) {
						status.append("可存檔");
						brainTrack = false;
						kinectTrack = false;
						setChanged();
						notifyObservers();
					}
				}
			}
			
			if (o instanceof SkelsManager) {
				if (arg instanceof String && arg.equals("kinect")) {
					kinect = " 體感偵測器：\n 追蹤中\n";
					setStatus(brainwave + "--------------------------\n" + kinect);
					kinectTrack = true;
					if (brainTrack && kinectTrack) {
						status.append("可存檔");
						brainTrack = false;
						kinectTrack = false;
						setChanged();
						notifyObservers();
					}
				}
			}
			
			if (o instanceof BrainwaveControl) {
				if (arg instanceof String && arg.equals("bok")) {
					brainOk = true;
					System.out.println(arg);
					if (brainOk && kinectOk && actOk) {
						isSaveOver = true;
						System.out.println(isSaveOver);
						brainOk = false;
						kinectOk = false;
						actOk = false;
//					setChanged();
//					notifyObservers("ok");
					}
				}
			}
			
			if (o instanceof SkelsManager) {
				if (arg instanceof String && arg.equals("kok")) {
					kinectOk = true;
					System.out.println(arg);
					if (brainOk && kinectOk && actOk) {
						isSaveOver = true;
						System.out.println(isSaveOver);
						brainOk = false;
						kinectOk = false;
						actOk = false;
//					setChanged();
//					notifyObservers("ok");
					}
				}
			}
			if (o instanceof SpatialSpatialDataListener) {
				if (arg instanceof String && arg.equals("aok")) {
					actOk = true;
					System.out.println(arg);
					if (brainOk && kinectOk && actOk) {
						isSaveOver = true;
						System.out.println(isSaveOver);
						brainOk = false;
						kinectOk = false;
						actOk = false;
//					setChanged();
//					notifyObservers("ok");
					}
				}
			}
		}
	}
}
