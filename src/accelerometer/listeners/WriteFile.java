package accelerometer.listeners;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import com.phidgets.event.SpatialDataEvent;
import com.phidgets.event.SpatialDataListener;

public class WriteFile extends Observable implements SpatialDataListener{

    private FileWriter fw;

	public WriteFile() {
		// TODO Auto-generated constructor stub
		
	}
	
	public void writeactivity(String write) {
    	try {
    		fw = new FileWriter("src/file/activity.csv");
    		int i = 0;
    		
    			try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			fw.append(write);
    			System.out.println(write);
    			
    			i++;
    		
    		fw.flush();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

	@Override
	public void data(SpatialDataEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
