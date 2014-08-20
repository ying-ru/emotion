package brain.control;

import org.apache.log4j.Logger;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.prefs.Preferences;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import org.json.JSONException;
import org.json.JSONObject;

import brain.preferences.PreferenceManager;
import brain.window.DebugWindow;
import brain.window.PreferencesWindow;
import brainwave.client.ThinkGearSocketClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.JDBC;

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
    private DebugWindow debugWindow;
    final  static PreferencesWindow preferencesWindow = new PreferencesWindow();
    
    /**
     * System tray launcher
     * 
     * @param args
     * @return void
     */
    
    public BrainwaveControl(DebugWindow debug) {
    	debugWindow = debug;
    	isTracking = "";
    	isStartWrite = false;
    }
    
//    public static void main(String[] args) {
        // TODO Set look and feel
        // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        /* Turn off metal's use of bold fonts */
//        UIManager.put("swing.boldMetal", Boolean.FALSE);

//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                initializeGUI();
//                actionConnected();
//                actionSaveFile();
//            }
//        });
//    }

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
//        debugWindow.setVisible(true);
//    	debugWindow.getTextArea().append("123\n");
        SwingWorker worker = new SwingWorker<Void, Void>() {
            public Void doInBackground() {
            	isDataAvailable = client.isDataAvailable();
                while (isDataAvailable) {
                	brainData = client.getData();
                    debugWindow.getTextArea().append(brainData + '\n');
                    debugWindow.getTextArea().setCaretPosition(debugWindow.getTextArea().getText().length());
                    
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
    
    // FIXME: File seems to not be saving if JSON output is viewed first
    public void actionSaveFile() {
//    	final String csvFile = PreferenceManager.loadPreferences().get("fileLocation", "");
    	
    	final JDBC jdbc =new JDBC();
    	
    	SwingWorker worker = new SwingWorker<Void, Void>() {
    		public Void doInBackground() {
//    			if (csvFile == null) {
//    				logger.debug("$SwingWorker<Void,Void>.doInBackground() - csvFile == null...");
//				}
    			
    			FileWriter writer = null;
    			String newLine = System.getProperty("line.separator");
    			
    			try {
    				writer = new FileWriter("src/file/mindstream.csv");
    			} catch (IOException e1) {
    				logger.debug("$SwingWorker<Void,Void>.doInBackground() - Error opening file for writing!");
    				logger.error("$SwingWorker<Void,Void>.doInBackground()", e1);
    			}
    			
//    			// HEADER
//    			try {
//    				writer.append("TIMESTAMP,");
//    				writer.append("LOW_ALPHA,HIGH_ALPHA,LOW_BETA,HIGH_BETA,");
//                  	writer.append("LOW_GAMMA,HIGH_GAMA");
//                  	writer.append(newLine);
//                  	
//    			} catch (IOException e2) {
//    				logger.debug("$SwingWorker<Void,Void>.doInBackground() - Write Error..." + e2.getMessage());
//    			}
    			
    			SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    			
    			int i = 0;
    			while (isDataAvailable && i < 30 ) {
//    				logger.debug("$SwingWorker<Void,Void>.doInBackground() - Writing...");
    				
//					logger.debug("$SwingWorker<Void,Void>.doInBackground() - " + client.getData());
    				try {
    					String clientData = brainData;
    					brainData = "";
//    					logger.debug("$SwingWorker<Void,Void>.doInBackground() - " + clientData);
    					JSONObject json = new JSONObject(clientData);
    					
    					/*
    					 * JH: check just in case it's not there due to poorSignallevel
    					 */
    					if (!json.isNull("eegPower")) {
    						String timeStamp = fmt.format(new Date());
							writer.append(timeStamp + ',');
    						
    						/*
    						 * JH: check for existence of poorSignalLevel. 
    						 * If not available, assume 0 
    						 */
    						
//							if (!json.isNull("poorSignalLevel")) {
//								writer.append(Integer.toString(json.getInt("poorSignalLevel")) + ',');
//							} else {
//								writer.append("0,");
//							}
    					
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
//								writer.append(Integer.toString(esense.getInt("attention")) + ',');
//								writer.append(Integer.toString(esense.getInt("meditation")) + ',');
    						} else {
//    							logger.debug("$SwingWorker<Void,Void>.doInBackground() - eSense is null!");
    						}
    						
    						JSONObject eegPower = json.getJSONObject("eegPower");
    						
//							writer.append(Integer.toString(eegPower.getInt("delta")) + ",");
//							writer.append(Integer.toString(eegPower.getInt("theta")) + ',');
    						writer.append(Integer.toString(eegPower.getInt("lowAlpha")) + ',');
    						writer.append(Integer.toString(eegPower.getInt("highAlpha")) + ',');
    						writer.append(Integer.toString(eegPower.getInt("lowBeta")) + ',');
    						writer.append(Integer.toString(eegPower.getInt("highBeta")) + ',');
    						writer.append(Integer.toString(eegPower.getInt("lowGamma")) + ',');
    						writer.append(Integer.toString(eegPower.getInt("highGamma")));
    						writer.append(newLine);
    						
//							jdbc.insert(timeStamp, eegPower.getInt("lowAlpha"),
//								eegPower.getInt("highAlpha"), eegPower.getInt("lowBeta"),
//								eegPower.getInt("highBeta"), eegPower.getInt("lowGamma"),
//								eegPower.getInt("highGamma"));
    						
    						i++;
    					} else {
//    						logger.debug("$SwingWorker<Void,Void>.doInBackground() - eegPower is null!");
    					}
    					
    					writer.flush();
    					
    				} catch (JSONException e1) {
//    					logger.debug("$SwingWorker<Void,Void>.doInBackground() - JSON Error" + e1.getMessage());
    				} catch (IOException e2) {
//    					logger.debug("$SwingWorker<Void,Void>.doInBackground() - Write Error" + e2.getMessage());
    				}
    			}
    			try {
//    				logger.debug("$SwingWorker<Void,Void>.doInBackground() - Closing file...");
    				writer.close();
    				isTracking = "bok";
					setChanged();
					notifyObservers(isTracking);
    			} catch (IOException e) {
//    				logger.debug("$SwingWorker<Void,Void>.doInBackground() - Write Error" + e.getMessage());
    			}
    			return null;
    		}
    	};
    	worker.execute();
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
    
    public void actionExit() {
        System.exit(0);
    }
    
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
//		actionSaveFile();
	}
}