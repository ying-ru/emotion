package emotion.ui;

public class BrainwaveBarChartPanel extends BarChartPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	private boolean isRunning;
	
	public BrainwaveBarChartPanel() {
		// TODO Auto-generated constructor stub
		super();
		new Thread(this).start(); // start updating the panel's image
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		isRunning = true;
		while (isRunning) {
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println("1" + spatialData_listener.update());
//			updateValue(200, "活動量");
		}
	}

}
