package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class EmotionFrame extends MainFrame {
	private JLabel background, forWhoToPlay;
	private JButton leaveBtn;
	private ImageIcon backgroundPhoto;
	private String userToken;
	private Thread playTooLong;
	private Thread updateScore;
	private boolean isRemind = false;
	
	public EmotionFrame() {
		initJPanel();
		initJButton();
		initJLabel();
		initBackground();
		initBound();
		setComponentFont();
		revalidate();
		repaint();
	}


	// init Component //

	private void setComponentFont() {
//		leaveBtn.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 40));
//		forWhoToPlay
//				.setFont(new Font(Font.DIALOG, Font.BOLD, getHeight() / 20));
	}

	private void initJButton() {
//		leaveBtn = new JButton("離開");
//		leaveBtn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					if (server.getRoom() != -1) {
//						server.s.exit(server.getRoom(), userToken);
//					}
//				} catch (RemoteException e1) {
//					e1.printStackTrace();
//				}
//				System.exit(0);
//			}
//		});
//		add(leaveBtn);
	}

	private void initJLabel() {
//		forWhoToPlay = new JLabel("輪到你了");
//		forWhoToPlay.setForeground(Color.red);
//		add(forWhoToPlay);
	}

	private void initBackground() { // 加入背景圖片
		backgroundPhoto = new ImageIcon(getClass().getResource("/image/background.png"));
		background = new JLabel();
		backgroundPhoto.setImage(backgroundPhoto.getImage().getScaledInstance(
				getWidth(), getHeight(), Image.SCALE_DEFAULT));// 設定圖片的顯示
		background.setIcon(backgroundPhoto);
		add(background);
	}

	// 設定Panel
	private void initJPanel() {
//		playerInfo = new PlayerInfoPanel(getWidth() / 30,
//				getHeight() / 90 * 62, (getWidth() - getWidth() / 10) * 2 / 3,
//				getHeight() / 9 * 2);
//		add(playerInfo);
//		chatArea = new ChatPanel(getWidth() - (getWidth() - getWidth() / 50)
//				/ 3, getHeight() / 9 * 3,
//				(getWidth() - getWidth() / 60 * 7) / 3, getHeight() / 90 * 52,
//				server, userToken);
//		add(chatArea);
	}

	private void initBound() {
//		background.setBounds(0, 0, this.getWidth(), this.getHeight());
//		forWhoToPlay.setBounds(getWidth() - getWidth() / 20 * 6,
//				getHeight() / 90 * 14, getWidth() / 4, getHeight() / 90 * 8);
//		leaveBtn.setBounds(getWidth() - getWidth() / 15 * 2,
//				getHeight() / 90 * 14, getWidth() / 10, getHeight() / 90 * 8);
	}
	
	// init Component end //
	
	public static void main(String args[]) {
		EmotionFrame e = new EmotionFrame();
		
	}
}
