package main;

import org.jfree.ui.RefineryUtilities;
import emotion.ui.EmotionFrame;

public class Main {
	public static void main(String args[]) {
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RefineryUtilities.centerFrameOnScreen(new EmotionFrame("情緒感測系統"));
            }
        });
	}
}
