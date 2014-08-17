package com.ericblue.mindstream.window;

import org.apache.log4j.Logger;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

/**
 * <p>Title:		DebugWindow</p><br>
 * <p>Description:	Displays real-time JSON output from ThinkGear socket</p><br>
 * @author		    <a href="http://eric-blue.com">Eric Blue</a><br>
 *
 * $Date: 2012-07-08 03:31:28 $ 
 * $Author: ericblue76 $
 * $Revision: 1.4 $
 *
 */


public class DebugWindow extends JPanel {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(DebugWindow.class);

	public JTextArea textArea;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public DebugWindow(int width, int height) {
		setBounds(100, 100, 828, 562);
		setBorder(new EmptyBorder(5, 5, 5, 5));

		SpringLayout sl_contentPane = new SpringLayout();
//		sl_contentPane.putConstraint(SpringLayout.WEST, btnClose, 363, SpringLayout.WEST, this);
//		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnClose, -10, SpringLayout.SOUTH, this);
		setLayout(sl_contentPane);

		this.textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(width, height));
		add(scrollPane);
	}

	public JTextArea getTextArea() {
		return textArea;
	}
}
