package main;

import org.jfree.ui.RefineryUtilities;
import emotion.ui.CopyOfEmotionFrame;

public class Main {
	public static void main(String args[]) {
		
//		java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                RefineryUtilities.centerFrameOnScreen(new EmotionFrame("情緒"));
//            }
//        });
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RefineryUtilities.centerFrameOnScreen(new CopyOfEmotionFrame("情緒"));
            }
        });
		
//		e.getTrackingPanel().getTrack().addObserver(e.getTrackingPanel().getTrackerPanel3D().getSkelsManager());
//		e.getTrackingPanel().getTrack().addObserver(e.getTrackingPanel().getCopyOfMindStreamSystemTray());
//		e.getTrackingPanel().getTrackerPanel3D().getSkelsManager().addObserver(e.getTrackingPanel().getTrack());
//		e.getTrackingPanel().getCopyOfMindStreamSystemTray().addObserver(e.getTrackingPanel().getTrack());
	}
}
