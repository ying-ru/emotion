package brainwave.control;

import org.apache.log4j.Logger;

import java.awt.Image;
import java.awt.SystemTray;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import org.json.JSONException;
import org.json.JSONObject;

import emotion.ui.BarChartPanel;
import emotion.ui.BarChartPanel;
import emotion.jdbc.JDBC;
import brainwave.client.ThinkGearSocketClient;
import brainwave.preferences.PreferenceManager;
import brainwave.window.DebugWindow;
import brainwave.window.PreferencesWindow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>Title:        MindStreamSystemTray</p><br>
 * <p>Description:  Description: System tray app for streaming data from the Neurosky MindSet/MindWave</p><br>
 * @author          <a href="http://eric-blue.com">Eric Blue</a><br>
 *
 * $Date: 2014-01-26 19:36:10 $ 
 * $Author: ericblue76 $
 * $Revision: 1.9 $
 *
 */


public class BrainwaveControl extends Observable implements Observer {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(BrainwaveControl.class);
    
    static String host = PreferenceManager.loadPreferences().get("thinkgearHost", "");
    static int port = PreferenceManager.loadPreferences().getInt("thinkgearPort", 0);
    
    final static ThinkGearSocketClient client = new ThinkGearSocketClient(host, port);
    private boolean isStartWrite, isDataAvailable;
    private String isTracking, brainData;
    private String value;
    private BarChartPanel barChartPanel;

	protected boolean canSave;

	protected FileWriter fw;
    final  static PreferencesWindow preferencesWindow = new PreferencesWindow();
    
    /**
     * System tray launcher
     * 
     * @param args
     * @return void
     */
    
    public BrainwaveControl(BarChartPanel brainwaveBarChart) {
    	initializeGUI();
    	barChartPanel = brainwaveBarChart;
//    	debugWindow = debug;
    	isTracking = "";
    	isStartWrite = false;
    	barChartPanel.updateValue(0D, "高α波");
    	barChartPanel.updateValue(0D, "低α波");
    	barChartPanel.updateValue(0D, "高β波");
    	barChartPanel.updateValue(0D, "低β波");
    	barChartPanel.updateValue(0D, "高γ波");
    	barChartPanel.updateValue(0D, "低γ波");
    	write();
    }

    /**
     * Initializes preferences on first time launch
     * 
     * @param none
     * @return void
     */
    private static void initializePreferences() {

        Preferences prefs = PreferenceManager.loadPreferences();

        if (prefs.get("thinkgearHost", null) == null) {
            logger.debug("initializePreferences() - Setting default ThinkGear Host");
            prefs.put("thinkgearHost", ThinkGearSocketClient.DEFAULT_HOST);
        }

        if (prefs.getInt("thinkgearPort", 0) == 0) {
            logger.debug("initializePreferences() - Setting default ThinkGear Host");
            prefs.putInt("thinkgearPort", ThinkGearSocketClient.DEFAULT_PORT);
        }

        if (prefs.get("fileLocation", null) == null) {
            logger.debug("initializePreferences() - Setting default CSV file location");
            String file = System.getProperty("user.home") + System.getProperty("file.separator");
            file += "mindstream.csv";
            prefs.put("fileLocation", file);
        }

    }

    /**
     * Initialize GUI
     * 
     * @param none
     * @return void
     */

    private static void initializeGUI() {
        // TODO Cleanup all System.out/.err with log4j calls
        // Check the SystemTray support
        if (!SystemTray.isSupported()) {
            logger.debug("initializeGUI() - SystemTray is not supported");
            return;
        }
        initializePreferences();
        // TODO Load default preferences if they haven't been initialized
    }

    // Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = BrainwaveControl.class.getResource(path);

        if (imageURL == null) {
            logger.error("createImage(String, String) - Resource not found: " + path, null);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
    
    public void actionPreferencesWindow() {
        preferencesWindow.setVisible(true);
        preferencesWindow.getContentPane().requestFocus();
    }
    
    public void actionDebugWindow() {
        SwingWorker worker = new SwingWorker<Void, Void>() {
            public Void doInBackground() {
            	isDataAvailable = client.isDataAvailable();
            	
                while (isDataAvailable) {
                	brainData = client.getData();
                	value = getPowerValue();
                	
                    if (value != null) {
                    	String[] tokens = value.split(",");
                    	barChartPanel.updateValue(Integer.parseInt(tokens[0]), "高α波");
                    	barChartPanel.updateValue(Integer.parseInt(tokens[1]), "低α波");
                    	barChartPanel.updateValue(Integer.parseInt(tokens[2]), "高β波");
                    	barChartPanel.updateValue(Integer.parseInt(tokens[3]), "低β波");
                    	barChartPanel.updateValue(Integer.parseInt(tokens[4]), "高γ波");
                    	barChartPanel.updateValue(Integer.parseInt(tokens[5]), "低γ波");
                    }
                }
                return null;
            }
        };
        worker.execute();
    }

    public void actionConnected() {
    	String host = PreferenceManager.loadPreferences().get("thinkgearHost", "");
    	int port = PreferenceManager.loadPreferences().getInt("thinkgearPort", 0);
    	
    	if (!client.isConnected()) {
    		try {
    			client.setHost(host);
    			client.setPort(port);
    			client.connect();
    			notifyTrack();
    		} catch (IOException e1) {
    			logger.debug("$Connected - Connection Error..." + e1.getMessage());
    		}
    	}
    }
    
    public void actionDisconnection() {
    	try {
    		client.close();
    	} catch (IOException e1) {
    		logger.debug("$Connected - Close Error..." + e1.getMessage());
    	}
    }
    
    public void notifyTrack() {
    	while (client.isDataAvailable() && !isTracking.equals("brain") && !isTracking.equals("bok")) {
    		if (client.getData().startsWith("{\"eSense\":{\"attention\":")) {
            	isTracking = "brain";
            	
            	setChanged();
        		notifyObservers(isTracking);
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
    	}
    }
    
    public String getPowerValue() {
    	String value = null;
    	String newLine = System.getProperty("line.separator");
    	try {
			String clientData = brainData;
//			brainData = "";
			JSONObject json = new JSONObject(clientData);
			
			/*
			 * JH: check just in case it's not there due to poorSignallevel
			 */
			if (!json.isNull("eegPower")) {				
				/*
				 * JH: check for existence of poorSignalLevel. 
				 * If not available, assume 0 
				 */
				
//				if (!json.isNull("poorSignalLevel")) {
//					writer.append(Integer.toString(json.getInt("poorSignalLevel")) + ',');
//				} else {
//					writer.append("0,");
//				}
			
				/*
				 * JH: check for existence of eSense. 
				 * I noticed it's possible to get eegPower
				 * without eSense when poorSignallevel >0
				 */
				if (!json.isNull("eSense")) {
					JSONObject esense = json.getJSONObject("eSense");
					
					/*
					 * JH: Don't know if it's possible
					 * for these attributes to not exist
					 * even when the JSON Object exists
					 */
//					writer.append(Integer.toString(esense.getInt("attention")) + ',');
//					writer.append(Integer.toString(esense.getInt("meditation")) + ',');
				} else {
//					logger.debug("$SwingWorker<Void,Void>.doInBackground() - eSense is null!");
				}
				
				JSONObject eegPower = json.getJSONObject("eegPower");
				
				value = Integer.toString(eegPower.getInt("lowAlpha")) + ',';
				value = value + Integer.toString(eegPower.getInt("highAlpha")) + ',';
				value = value + Integer.toString(eegPower.getInt("lowBeta")) + ',';
				value = value + Integer.toString(eegPower.getInt("highBeta")) + ',';
				value = value + Integer.toString(eegPower.getInt("lowGamma")) + ',';
				value = value + Integer.toString(eegPower.getInt("highGamma"));
				return value;
			} else {
//				logger.debug("$SwingWorker<Void,Void>.doInBackground() - eegPower is null!");
			}
		} catch (JSONException e1) {
//			logger.debug("$SwingWorker<Void,Void>.doInBackground() - JSON Error" + e1.getMessage());
		}
		return value;
    }
    
    public void setWrite() {
    	canSave = true;
    }
    
    public void write() {
		Thread update = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
					int i = 0;
					while (true) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (isDataAvailable && canSave && i < 10) {
							if (i == 0) {
								fw = new FileWriter("src/file/mindstream.csv");
							}
							if (value != null) {
								String timeStamp = fmt.format(new Date());
								
								fw.append("'" + timeStamp + "'" + ',');
								fw.append(value + '\n');
								value = null;
								i++;
							}
						} else if (i >= 10) {
							fw.flush();
							fw.close();
							i = 0;
							canSave = false;
							isTracking = "bok";
							setChanged();
							notifyObservers(isTracking);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		update.start();
	}
    
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
//		actionSaveFile();
	}
}