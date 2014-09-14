/* - SpatialSpatialDataListener -
 *  Display the spatial data after a spatial data change
 *
 * Copyright 2011 Phidgets Inc.
 * This work is licensed under the Creative Commons Attribution 2.5 Canada License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by/2.5/ca/
 */
package accelerometer.listeners;

import accelerometer.graphics.CompassBearingGraphPanel;
import accelerometer.graphics.GyroGraphPanel;
import accelerometer.graphics.MagFieldGraphPanel;
import accelerometer.graphics.MotionGraphPanel;
import accelerometer.math.Matrix3x3;
import accelerometer.math.Vector3;

import com.phidgets.SpatialEventData;
import com.phidgets.SpatialPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.SpatialDataListener;
import com.phidgets.event.SpatialDataEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;

public class SpatialSpatialDataListener extends Observable implements SpatialDataListener {

    private JTextField accelXTxt;
    private JTextField accelYTxt;
    private JTextField accelZTxt;
    private JTextField gyroXTxt;
    private JTextField gyroYTxt;
    private JTextField gyroZTxt;
    private JTextField gyroXTxt1;
    private JTextField gyroYTxt1;
    private JTextField gyroZTxt1;
    private Double[] gyroHeading;
    private Double lastTime;
    private JTextField pitchAngleTxt;
    private JTextField rollAngleTxt;
    private JTextField bearingTxt;
    private ArrayList<Double[]> compassBearingFilter;
    private MotionGraphPanel graphPanel;
    private MagFieldGraphPanel magFieldGraphPanel;
    private GyroGraphPanel gyroGraphPanel;
    private double compassBearing;
    private CompassBearingGraphPanel compassBearingGraphPanel;
    private final char DEGREESYMBOL = '\u00b0';
    private FileWriter fw;
    private String write;
    private String isTracking;
    private double x, y, z;
    private double actValue;
    private boolean canSave;

    public SpatialSpatialDataListener(JTextField accelXTxt, JTextField accelYTxt, JTextField accelZTxt,
            JTextField gyroXTxt, JTextField gyroYTxt, JTextField gyroZTxt, JTextField gyroXTxt1, JTextField gyroYTxt1, JTextField gyroZTxt1,
            Double[] gyroHeading, Double lastTime, JTextField pitchAngleTxt, JTextField rollAngleTxt, JTextField bearingTxt,
            ArrayList<Double[]> compassBearingFilter, MotionGraphPanel graphPanel, MagFieldGraphPanel magFieldGraphPanel,
            GyroGraphPanel gyroGraphPanel, CompassBearingGraphPanel compassBearingGraphPanel) {
        this.accelXTxt = accelXTxt;
        this.accelYTxt = accelYTxt;
        this.accelZTxt = accelZTxt;
        this.gyroXTxt = gyroXTxt;
        this.gyroYTxt = gyroYTxt;
        this.gyroZTxt = gyroZTxt;
        this.gyroXTxt1 = gyroXTxt1;
        this.gyroYTxt1 = gyroYTxt1;
        this.gyroZTxt1 = gyroZTxt1;
        this.gyroHeading = gyroHeading;
        this.lastTime = lastTime;
        this.pitchAngleTxt = pitchAngleTxt;
        this.rollAngleTxt = rollAngleTxt;
        this.bearingTxt = bearingTxt;
        this.compassBearingFilter = compassBearingFilter;
        this.graphPanel = graphPanel;
        this.magFieldGraphPanel = magFieldGraphPanel;
        this.gyroGraphPanel = gyroGraphPanel;
        this.compassBearingGraphPanel = compassBearingGraphPanel;
        compassBearing = 0.0;
        write = "";
        isTracking = "0,0,0";
        canSave = false;
        x = 0;
        y = 0;
        z = 0;
        actValue = 0;
        write();
    }

    public Double getLastTime() {
        return this.lastTime;
    }
    
    public double update() {
    	return actValue;
    }

    public void data(SpatialDataEvent sde) {

        SpatialPhidget spatial = (SpatialPhidget) sde.getSource();
        
        try {
            if (spatial.getAccelerationAxisCount() > 0) {
            	actValue = Math.abs(((Math.abs(roundDouble((sde.getData()[0].getAcceleration()[0]), 3) * 1000 - x) + 
                		Math.abs(roundDouble((sde.getData()[0].getAcceleration()[1]), 3) * 1000 - y) + 
        				Math.abs(roundDouble((sde.getData()[0].getAcceleration()[2]), 3) * 1000 - z)) / 3));
                
            	accelXTxt.setText(Double.toString(roundDouble((sde.getData()[0].getAcceleration()[0]), 3)));
            	accelYTxt.setText(Double.toString(roundDouble((sde.getData()[0].getAcceleration()[1]), 3)));
                accelZTxt.setText(Double.toString(roundDouble((sde.getData()[0].getAcceleration()[2]), 3)));

                write = Double.toString(Math.abs(roundDouble((sde.getData()[0].getAcceleration()[0]), 3) * 1000 - x)) + ","
                		+ Double.toString(Math.abs(roundDouble((sde.getData()[0].getAcceleration()[1]), 3) * 1000 - y)) + ","
                		+ Double.toString(Math.abs(roundDouble((sde.getData()[0].getAcceleration()[2]), 3) * 1000 - z)) + "\n";
                
                x = roundDouble((sde.getData()[0].getAcceleration()[0]), 3) * 1000;
                y = roundDouble((sde.getData()[0].getAcceleration()[1]), 3) * 1000;
                z = roundDouble((sde.getData()[0].getAcceleration()[2]), 3) * 1000;
                
                displayAccelGraph(sde.getData()[0].getAcceleration(), graphPanel);
            }

        } catch (PhidgetException ex) {
            Logger.getLogger(SpatialSpatialDataListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (spatial.getGyroAxisCount() > 0) {

                calculateGyroHeading(sde.getData());
                gyroXTxt.setText(Double.toString(roundDouble(gyroHeading[0], 3)) + DEGREESYMBOL);
                gyroYTxt.setText(Double.toString(roundDouble(gyroHeading[1], 3)) + DEGREESYMBOL);
                gyroZTxt.setText(Double.toString(roundDouble(gyroHeading[2], 3)) + DEGREESYMBOL);
                displayGyroGraph(gyroGraphPanel);
            }

        } catch (PhidgetException ex) {
            Logger.getLogger(SpatialSpatialDataListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //Even when there is a compass chip, sometimes there won't be valid data in the event.
            if ((spatial.getCompassAxisCount() > 0) && (sde.getData()[0].getMagneticField().length > 0)) {

                gyroXTxt1.setText(Double.toString(roundDouble((sde.getData()[0].getMagneticField()[0]), 3)));
                gyroYTxt1.setText(Double.toString(roundDouble((sde.getData()[0].getMagneticField()[1]), 3)));
                gyroZTxt1.setText(Double.toString(roundDouble((sde.getData()[0].getMagneticField()[2]), 3)));

                try {

                    displayMagFieldGraph(sde.getData()[0].getMagneticField(), magFieldGraphPanel);
                    calculateCompassBearing(sde.getData());
                    displayCompassBearingGraph(compassBearingGraphPanel);
                } catch (Exception ex) {
                }
            }
        } catch (PhidgetException ex) {
            Logger.getLogger(SpatialSpatialDataListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void calculateGyroHeading(SpatialEventData[] data) {
        int i;

        Double time = data[0].getTime() * 1000.0;
        if (lastTime == 0) {
            lastTime = time;
        }

        for (i = 0; i < 3; i++) {
            gyroHeading[i] += data[0].getAngularRate()[i] * (((time) - lastTime) / 1000.0);
        }

        lastTime = time;
    }
    //This finds a magnetic north bearing, correcting for board tilt and roll as measured by the accelerometer
    //This doesn't account for dynamic acceleration - ie accelerations other then gravity will throw off the calculation
    double lastBearing = 0;

    public void calculateCompassBearing(SpatialEventData[] spatialData) {

        double Xh = 0;
        double Yh = 0;

        //find the tilt of the board with respect to gravity
        Vector3 gravity = Vector3.Normalize(new Vector3(
                spatialData[0].getAcceleration()[0],
                spatialData[0].getAcceleration()[2],
                spatialData[0].getAcceleration()[1]));

        double pitchAngle = Math.asin(gravity.X);
        double rollAngle = Math.asin(gravity.Z);

        //The board is up-side down
        if (gravity.Y < 0) {
            pitchAngle = -pitchAngle;
            rollAngle = -rollAngle;

        }

        //Construct a rotation matrix for rotating vectors measured in the body frame, into the earth frame
        //this is done by using the angles between the board and the gravity vector.
        Matrix3x3 xRotMatrix = new Matrix3x3();

        xRotMatrix.matrix[0][0] = Math.cos(pitchAngle);
        xRotMatrix.matrix[1][0] = -Math.sin(pitchAngle);
        xRotMatrix.matrix[2][0] = 0;
        xRotMatrix.matrix[0][1] = Math.sin(pitchAngle);
        xRotMatrix.matrix[1][1] = Math.cos(pitchAngle);
        xRotMatrix.matrix[2][1] = 0;
        xRotMatrix.matrix[0][2] = 0;
        xRotMatrix.matrix[1][2] = 0;
        xRotMatrix.matrix[2][2] = 1;

        Matrix3x3 zRotMatrix = new Matrix3x3();
        zRotMatrix.matrix[0][0] = 1;
        zRotMatrix.matrix[1][0] = 0;
        zRotMatrix.matrix[2][0] = 0;
        zRotMatrix.matrix[0][1] = 0;
        zRotMatrix.matrix[1][1] = Math.cos(rollAngle);
        zRotMatrix.matrix[2][1] = -Math.sin(rollAngle);
        zRotMatrix.matrix[0][2] = 0;
        zRotMatrix.matrix[1][2] = Math.sin(rollAngle);
        zRotMatrix.matrix[2][2] = Math.cos(rollAngle);

        Matrix3x3 rotMatrix = Matrix3x3.Multiply(xRotMatrix, zRotMatrix);

        Vector3 data = new Vector3(
                spatialData[0].getMagneticField()[0],
                spatialData[0].getMagneticField()[2],
                -spatialData[0].getMagneticField()[1]);

        Vector3 correctedData = Matrix3x3.Multiply(data, rotMatrix);

        //These represent the x and y components of the magnetic field vector in the earth frame
        Xh = -correctedData.Z;
        Yh = -correctedData.X;

        //we use the computed X-Y to find a magnetic North bearing in the earth frame
        try {
            double bearing = 0.0;
            double _360inRads = (360.0 * Math.PI / 180.0);
            if (Xh < 0.0) {

                bearing = Math.PI - Math.atan(Yh / Xh);
            } else if ((Xh > 0.0) && (Yh < 0.0)) {
                bearing = -Math.atan(Yh / Xh);
            } else if ((Xh > 0.0) && (Yh > 0.0)) {
                bearing = Math.PI * 2.0 - (Math.atan(Yh / Xh));
            } else if ((Xh == 0.0) && (Yh < 0.0)) {
                bearing = Math.PI / 2.0;
            } else if ((Xh == 0.0) && (Yh > 0.0)) {
                bearing = Math.PI * 1.5;
            }

            //The board is up-side down
            if (gravity.Y < 0) {
                bearing = Math.abs(bearing - _360inRads);
            }

            //passing the 0 <-> 360 point, need to make sure the filter never contains both values near 0 and values near 360 at the same time.
            if (Math.abs(bearing - lastBearing) > 2) //2 radians == ~115 degrees
            {
                if (bearing > lastBearing) {
                    for (Double[] stuff : compassBearingFilter) {
                        stuff[0] += _360inRads;
                    }
                } else {
                    for (Double[] stuff : compassBearingFilter) {
                        stuff[0] -= _360inRads;
                    }

                }
            }

            Double[] temp = {bearing, pitchAngle, rollAngle};
            int compassBearingFilterSize = 10;
            compassBearingFilter.add(temp);
            if (compassBearingFilter.size() > compassBearingFilterSize) {
                compassBearingFilter.remove(0);
            }

            bearing = 0;
            pitchAngle = 0;
            rollAngle = 0;

            for (Double[] stuff : compassBearingFilter) {
                bearing += stuff[0];
                pitchAngle += stuff[1];
                rollAngle += stuff[2];
            }

            bearing /= compassBearingFilter.size();
            pitchAngle /= compassBearingFilter.size();
            rollAngle /= compassBearingFilter.size();

            compassBearing = bearing * (180.0 / Math.PI);
            lastBearing = bearing;

            bearingTxt.setText(Double.toString(roundDouble(bearing * (180.0 / Math.PI), 1)) + DEGREESYMBOL);

            pitchAngleTxt.setText(Double.toString(roundDouble(pitchAngle * (180.0 / Math.PI), 1)) + DEGREESYMBOL);

            rollAngleTxt.setText(Double.toString(roundDouble(rollAngle * (180.0 / Math.PI), 1)) + DEGREESYMBOL);

        } catch (Exception ex) {
        }
    }

    private void displayAccelGraph(double[] accelData, MotionGraphPanel graphPanel) {
        graphPanel.setXOut(accelData[0]);
        graphPanel.setYOut(accelData[1]);
        graphPanel.setZOut(accelData[2]);
        graphPanel.repaint();
    }

    private void displayMagFieldGraph(double[] magFieldData, MagFieldGraphPanel graphPanel) {
        graphPanel.setXOut(magFieldData[0]);
        graphPanel.setYOut(magFieldData[1]);
        graphPanel.setZOut(magFieldData[2]);
        graphPanel.repaint();
    }

    private void displayGyroGraph(GyroGraphPanel graphPanel) {
        graphPanel.setXOut(gyroHeading[0]);
        graphPanel.setYOut(gyroHeading[1]);
        graphPanel.setZOut(gyroHeading[2]);
        graphPanel.repaint();
    }

    private void displayCompassBearingGraph(CompassBearingGraphPanel graphPanel) {
        graphPanel.setCompassBearing(compassBearing);
        graphPanel.repaint();
    }

    private double roundDouble(Double value, int decimalPlaces) {
        BigDecimal bd = new BigDecimal(value).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
        return (bd.doubleValue());
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
						
						if (canSave && i < 10) {
							if (i == 0) {
								fw = new FileWriter("src/file/activity.csv");
							}
							String timeStamp = fmt.format(new Date());
							fw.append(timeStamp + ',');
							fw.append(write);
							i++;
						} else if (i >= 10) {
							
							fw.flush();
							fw.close();
							i = 0;
							canSave = false;
							isTracking = "aok";
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
}
