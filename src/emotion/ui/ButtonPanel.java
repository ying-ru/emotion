package emotion.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import skeletons3D.TrackerPanel3D;
import accelerometer.listeners.SpatialSpatialDataListener;
import brainwave.control.BrainwaveControl;

public class ButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton connect, save, disconnection;
	private BrainwaveControl brainwave;
	private TrackerPanel3D tp3D;
	private SpatialSpatialDataListener ssdl;

//	private BrainwavePanel brainwavePanel;
	
	public ButtonPanel(int locationX, int locationY, int width, int height, 
			BrainwaveControl brainwave, TrackerPanel3D tp3D, SpatialSpatialDataListener ssdl) {
		this.brainwave = brainwave;
		this.tp3D = tp3D;
		this.ssdl = ssdl;
		setSize(width, height);
		setLocation(locationX, locationY);
		setLayout(null);
		setOpaque(false);
		
		initJButton();
		setComponentFont();
	}
	
	private void setComponentFont() {
		connect.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 7));
		save.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 7));
		disconnection.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 7));
	}
	
	private void initJButton() {
		connect = new JButton("腦波連線");
		connect.setForeground(Color.white);
		
		connect.setSize(getWidth(), getHeight() / 4);
		connect.setLocation(0, 0);
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				brainwave.actionConnected();
				brainwave.actionWindow();
			}
		});
		add(connect);
		
		disconnection = new JButton("腦波中斷");
		disconnection.setForeground(Color.white);
		disconnection.setSize(getWidth(), getHeight() / 4);
		disconnection.setLocation(0, getHeight() / 3);
		disconnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				brainwave.actionDisconnection();
			}
		});
		add(disconnection);
		
		save = new JButton("開始偵測");
		save.setSize(getWidth(), getHeight() / 4);
		save.setLocation(0, getHeight() *2 / 3);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				brainwave.setWrite();
				ssdl.setWrite();
				tp3D.getSkelsManager().setWrite();;
			}
		});
		add(save);
	}
}
