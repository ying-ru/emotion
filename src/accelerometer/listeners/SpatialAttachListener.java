/* - SpatialAttachListener -
 * populate the fields and controls
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
import accelerometer.spatial.CopyOfSpatial;

import com.phidgets.SpatialPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.AttachListener;
import com.phidgets.event.AttachEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SpatialAttachListener implements AttachListener {

    private JFrame appFrame;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JTextField attachedTxt;
    private JTextArea nameTxt;
    private JTextField serialTxt;
    private JTextField versionTxt;
    private JTextField numAccelAxesTxt;
    private JTextField numGyroAxesTxt;
    private JTextField numCompassAxesTxt;
    private JSlider dataRateScrl;
    private JTextField dataRateTxt;
    private JTextField accelXTxt;
    private JTextField accelYTxt;
    private JTextField accelZTxt;
    private JTextField gyroXTxt;
    private JTextField gyroYTxt;
    private JTextField gyroZTxt;
    private JButton zeroGyroBtn;
    private JTextField gyroXTxt1;
    private JTextField gyroYTxt1;
    private JTextField gyroZTxt1;
    private JTextField pitchAngleTxt;
    private JTextField rollAngleTxt;
    private JTextField bearingTxt;
    private Double[] gyroHeading;
    private Double lastTime;
    private MotionGraphPanel graphPanel;
    private MagFieldGraphPanel magFieldGraphPanel;
    private GyroGraphPanel gyroGraphPanel;
    private CompassBearingGraphPanel compassBearingGraphPanel;

    public SpatialAttachListener(JPanel jPanel2, JPanel jPanel3, JPanel jPanel4,
            JPanel jPanel5, JTextField attachedTxt, JTextArea nameTxt, JTextField serialTxt,
            JTextField versionTxt, JTextField numAccelAxesTxt, JTextField numGyroAxesTxt, JTextField numCompassAxesTxt,
            JSlider dataRateScrl, JTextField dataRateTxt, JTextField accelXTxt, JTextField accelYTxt, JTextField accelZTxt,
            JTextField gyroXTxt, JTextField gyroYTxt, JTextField gyroZTxt, JButton zeroGyroBtn, JTextField gyroXTxt1,
            JTextField gyroYTxt1, JTextField gyroZTxt1, JTextField pitchAngleTxt, JTextField rollAngleTxt, JTextField bearingTxt,
            Double[] gyroHeading, Double lastTime, MotionGraphPanel graphPanel, MagFieldGraphPanel magFieldGraphPanel,
            GyroGraphPanel gyroGraphPanel, CompassBearingGraphPanel compassBearingGraphPanel) {
        this.attachedTxt = attachedTxt;
        this.nameTxt = nameTxt;
        this.serialTxt = serialTxt;
        this.versionTxt = versionTxt;
        this.numAccelAxesTxt = numAccelAxesTxt;
        this.numGyroAxesTxt = numGyroAxesTxt;
        this.numCompassAxesTxt = numCompassAxesTxt;
        this.dataRateScrl = dataRateScrl;
        this.dataRateTxt = dataRateTxt;
        this.accelXTxt = accelXTxt;
        this.accelYTxt = accelYTxt;
        this.accelZTxt = accelZTxt;
        this.gyroXTxt = gyroXTxt;
        this.gyroYTxt = gyroYTxt;
        this.gyroZTxt = gyroZTxt;
        this.zeroGyroBtn = zeroGyroBtn;
        this.gyroXTxt1 = gyroXTxt1;
        this.gyroYTxt1 = gyroYTxt1;
        this.gyroZTxt1 = gyroZTxt1;
        this.pitchAngleTxt = pitchAngleTxt;
        this.rollAngleTxt = rollAngleTxt;
        this.bearingTxt = bearingTxt;
        this.gyroHeading = gyroHeading;
        this.lastTime = lastTime;
        this.graphPanel = graphPanel;
        this.magFieldGraphPanel = magFieldGraphPanel;
        this.gyroGraphPanel = gyroGraphPanel;
        this.compassBearingGraphPanel = compassBearingGraphPanel;

    }

    public void attached(AttachEvent an) {

        try {
            SpatialPhidget attached = (SpatialPhidget) an.getSource();

            attachedTxt.setText(Boolean.toString(attached.isAttached()));
            nameTxt.setText(attached.getDeviceName());
            serialTxt.setText(Integer.toString(attached.getSerialNumber()));
            versionTxt.setText(Integer.toString(attached.getDeviceVersion()));
            numAccelAxesTxt.setText(Integer.toString(attached.getAccelerationAxisCount()));
            numGyroAxesTxt.setText(Integer.toString(attached.getGyroAxisCount()));
            numCompassAxesTxt.setText(Integer.toString(attached.getCompassAxisCount()));

            if (attached.getAccelerationAxisCount() > 0) {
                //   jPanel2.setEnabled(true);
                //  jPanel2.setVisible(true);
                //jPanel3.setEnabled(true);
                //jPanel3.setVisible(true);
                graphPanel.setEnabled(true);
                graphPanel.setVisible(true);
                graphPanel.setExist(true);
                accelXTxt.setEnabled(true);
                accelYTxt.setEnabled(true);
                accelZTxt.setEnabled(true);

                dataRateTxt.setEnabled(true);
                dataRateScrl.setEnabled(true);
                dataRateScrl.setMinimum((int) Math.ceil(attached.getDataRateMax()));
                dataRateScrl.setMaximum((int) attached.getDataRateMin());
                dataRateScrl.setValue(1000);
                dataRateTxt.setText(Integer.toString(dataRateScrl.getValue()));

                if (attached.getAccelerationAxisCount() > 2) {
                    graphPanel.setZAxisExist(true);
                } else {
                    graphPanel.setZAxisExist(false);
                }
            }

            if (attached.getGyroAxisCount() > 0) {
                gyroXTxt.setEnabled(true);
                gyroYTxt.setEnabled(true);
                gyroZTxt.setEnabled(true);
                zeroGyroBtn.setEnabled(true);

                gyroGraphPanel.setEnabled(true);
                gyroGraphPanel.setVisible(true);
                gyroGraphPanel.setExist(true);
                gyroGraphPanel.setExist(true);
            }

            if (attached.getCompassAxisCount() > 0) {
                gyroXTxt1.setEnabled(true);
                gyroYTxt1.setEnabled(true);
                gyroZTxt1.setEnabled(true);
                pitchAngleTxt.setEnabled(true);
                rollAngleTxt.setEnabled(true);
                bearingTxt.setEnabled(true);

                compassBearingGraphPanel.setEnabled(true);
                compassBearingGraphPanel.setVisible(true);
                compassBearingGraphPanel.setExist(true);

                magFieldGraphPanel.setEnabled(true);
                magFieldGraphPanel.setVisible(true);
                magFieldGraphPanel.setExist(true);
                magFieldGraphPanel.setCompassAxesExist(true);

            }

        } catch (PhidgetException ex) {
            JOptionPane.showMessageDialog(appFrame, ex.getDescription(), "Phidget error " + ex.getErrorNumber(), JOptionPane.ERROR_MESSAGE);
        }
        gyroHeading[0] = 0.0;
        gyroHeading[1] = 0.0;
        gyroHeading[2] = 0.0;
        lastTime = 0.0;
    }
}
