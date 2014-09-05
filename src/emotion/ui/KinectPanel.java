package emotion.ui;

import javax.swing.JPanel;
import skeletons3D.TrackerPanel3D;

public class KinectPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private TrackerPanel3D tp3D;
    
	public KinectPanel(int locationX, int locationY, int width, int height) {
		// TODO Auto-generated constructor stub
		setSize(width, height);
		setLocation(locationX, locationY);
		setLayout(null);
		setOpaque(false);
		initJPanel();
	}
	
	private void initJPanel() {
        tp3D = new TrackerPanel3D();
		tp3D.setBounds(0, 0, 512, 512);
		add(tp3D);
	}
	
	public TrackerPanel3D getKinect() {
		return tp3D;
	}
}
