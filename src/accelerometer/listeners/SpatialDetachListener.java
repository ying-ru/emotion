/* - SpatialDetachListener -
 * Clear all the fields and disable all the controls
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

import com.phidgets.SpatialPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.DetachListener;
import com.phidgets.event.DetachEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SpatialDetachListener implements DetachListener {

    private JFrame appFrame;
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

    public SpatialDetachListener(JTextField attachedTxt, JTextArea nameTxt, JTextField serialTxt,
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

    public void detached(DetachEvent de) {
        try {

            SpatialPhidget detached = (SpatialPhidget) de.getSource();
            attachedTxt.setText(Boolean.toString(detached.isAttached()));
            nameTxt.setText("");
            serialTxt.setText("");
            versionTxt.setText("");
            numAccelAxesTxt.setText("");
            numGyroAxesTxt.setText("");
            numCompassAxesTxt.setText("");

            dataRateScrl.setEnabled(false);
            dataRateTxt.setText("");
            dataRateTxt.setEnabled(false);
            accelXTxt.setText("");
            accelXTxt.setEnabled(false);
            accelYTxt.setText("");
            accelYTxt.setEnabled(false);
            accelZTxt.setText("");
            accelZTxt.setEnabled(false);
            gyroXTxt.setText("");
            gyroXTxt.setEnabled(false);
            gyroYTxt.setText("");
            gyroYTxt.setEnabled(false);
            gyroZTxt.setText("");
            gyroZTxt.setEnabled(false);
            zeroGyroBtn.setEnabled(false);
            gyroXTxt1.setText("");
            gyroXTxt1.setEnabled(false);
            gyroYTxt1.setText("");
            gyroYTxt1.setEnabled(false);
            gyroZTxt1.setText("");
            gyroZTxt1.setEnabled(false);
            pitchAngleTxt.setText("");
            pitchAngleTxt.setEnabled(false);
            rollAngleTxt.setText("");
            rollAngleTxt.setEnabled(false);
            bearingTxt.setText("");
            bearingTxt.setEnabled(false);

            graphPanel.setEnabled(false);
            graphPanel.setVisible(false);
            graphPanel.setExist(false);

            magFieldGraphPanel.setEnabled(false);
            magFieldGraphPanel.setVisible(false);
            magFieldGraphPanel.setExist(false);

            gyroGraphPanel.setEnabled(false);
            gyroGraphPanel.setVisible(false);
            gyroGraphPanel.setExist(false);

            compassBearingGraphPanel.setEnabled(false);
            compassBearingGraphPanel.setVisible(false);
            compassBearingGraphPanel.setExist(false);

        } catch (PhidgetException ex) {
            JOptionPane.showMessageDialog(appFrame, ex.getDescription(), "Phidget error " + ex.getErrorNumber(), JOptionPane.ERROR_MESSAGE);
        }
    }
}
