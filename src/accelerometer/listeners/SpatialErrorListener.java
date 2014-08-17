/* - SpatialErrorListener -
 * Display the error description in a messagebox
 *
 * Copyright 2011 Phidgets Inc.
 * This work is licensed under the Creative Commons Attribution 2.5 Canada License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by/2.5/ca/
 */
package accelerometer.listeners;

import com.phidgets.event.ErrorListener;
import com.phidgets.event.ErrorEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SpatialErrorListener implements ErrorListener {

    private JFrame appFrame;

    public SpatialErrorListener(JFrame appFrame) {
        this.appFrame = appFrame;
    }

    public void error(ErrorEvent errorEvent) {
        JOptionPane.showMessageDialog(appFrame, errorEvent.toString(), "Spatial Error Event", JOptionPane.ERROR_MESSAGE);
    }
}
