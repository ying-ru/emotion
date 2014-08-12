package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel {

	private JLabel loading;
	private ImageIcon loadingImage;

	public LoadingPanel(int locationX, int locationY, int width, int height) {
		setSize(width, height);
		setLocation(locationX, locationY);
		setLayout(null);
		setOpaque(false);
		initImageIcon();
		initJLabel();
		initBound();
		initLocation();
		setComponentFont();
	}

	// init Component //

	private void setComponentFont() {
//		playerAWin.setFont(new Font(Font.DIALOG, Font.BOLD,
//				getHeight() * 7 / 40));
	}

	private void initImageIcon() {
		loadingImage = new ImageIcon(getClass().getResource(
				"/image/loading.png"));
	}

	private void initJLabel() {

		loading = new JLabel();
		loading.setSize((getWidth() - 20) / 4, (getWidth() - 20) / 4);
		loadingImage.setImage(loadingImage.getImage()
				.getScaledInstance(loading.getWidth(),
						loading.getHeight(), Image.SCALE_DEFAULT));// 設定圖片的顯示
		loading.setIcon(loadingImage);
		add(loading);
	}

	private void initBound() {
//		playerAWin.setBounds((getWidth() / 2) - (getWidth() / 4), 0,
//				(getWidth() - 20) / 4, (getWidth() - 20) / 18);
	}

	private void initLocation() {
		loading.setLocation(0, 0);
	}
	// init Component end //

	// API//
	// API end //
}
