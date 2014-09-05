package emotion.ui;

import accelerometer.listeners.SpatialSpatialDataListener;

public class AccelerometerBarChartPanel extends BarChartPanel implements
		Runnable {
	private static final long serialVersionUID = 1L;
	public SpatialSpatialDataListener spatialData_listener;
	private boolean isRunning;
	
	public AccelerometerBarChartPanel(SpatialSpatialDataListener ssdl) {
		// TODO Auto-generated constructor stub
		super();
		spatialData_listener = ssdl;
		setTitle("活動量");
        setRange(1000);
        setTickLabelsVisible(false);
        setValueVisible(true);
		new Thread(this).start(); // start updating the panel's image
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		isRunning = true;
		while (isRunning) {
			updateValue(spatialData_listener.update(), "活動量");
		}
	}

}
