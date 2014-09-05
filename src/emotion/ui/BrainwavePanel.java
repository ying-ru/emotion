package emotion.ui;

import javax.swing.JPanel;
import brainwave.control.BrainwaveControl;

public class BrainwavePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private BrainwaveControl brainwave;
	private BrainwaveBarChartPanel barChartPanel;
    
	public BrainwavePanel(int locationX, int locationY, int width, int height) {
		// TODO Auto-generated constructor stub
		setSize(width, height);
		setLocation(locationX, locationY);
		setLayout(null);
		setOpaque(false);
		initJPanel();
		brainwave = new BrainwaveControl(barChartPanel);
	}
	
	private void initJPanel() {
		barChartPanel = new BrainwaveBarChartPanel();
		barChartPanel.setBounds(0, 0, getWidth(), getHeight());
        add(barChartPanel);
	}
	
	public BrainwaveControl getBrainwave() {
		return brainwave;
	}
}
