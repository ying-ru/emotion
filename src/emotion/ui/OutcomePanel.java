package emotion.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class OutcomePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea proposal;
	private JLabel line0, line1, line2, line3, line4, 
		line5, line6, line7, line8, line9, line10, line11, line12, 
		line13, line14, line15, smile, sad;
	private ImageIcon line0Photo, line1Photo, line2Photo, line3Photo, 
		line4Photo, line5Photo, line6Photo, line7Photo, line8Photo, 
		smilePhoto, sadPhoto;
	private int value;

	public OutcomePanel(int locationX, int locationY, int width, int height, int value) {
		this.value = value;
		setSize(width, height);
		setLocation(locationX, locationY);
		setLayout(null);
		setOpaque(false);
		initImageIcon();
		initJLabel();
		initImage();
		initBound();
		initLocation();
		initLines();
		setComponentFont();
	}

	// init Component //

	private void setComponentFont() {
		proposal.setFont(new Font(Font.DIALOG, Font.BOLD,
				getHeight() * 2 / 40));
	}

	private void initImageIcon() {
		line0Photo = new ImageIcon(getClass().getResource("/image/line0.png"));
		line1Photo = new ImageIcon(getClass().getResource("/image/line1.png"));
		line2Photo = new ImageIcon(getClass().getResource("/image/line2.png"));
		line3Photo = new ImageIcon(getClass().getResource("/image/line3.png"));
		line4Photo = new ImageIcon(getClass().getResource("/image/line4.png"));
		line5Photo = new ImageIcon(getClass().getResource("/image/line5.png"));
		line6Photo = new ImageIcon(getClass().getResource("/image/line6.png"));
		line7Photo = new ImageIcon(getClass().getResource("/image/line7.png"));
		line8Photo = new ImageIcon(getClass().getResource("/image/line8.png"));
		smilePhoto = new ImageIcon(getClass().getResource("/image/smile.png"));
		sadPhoto = new ImageIcon(getClass().getResource("/image/sad.png"));
	}
	
	private void initImage() {
		line0Photo.setImage(line0Photo.getImage().getScaledInstance(
				line0.getWidth(),line0.getHeight(), Image.SCALE_DEFAULT));
		line1Photo.setImage(line1Photo.getImage().getScaledInstance(
				line0.getWidth(),line0.getHeight(), Image.SCALE_DEFAULT));
		line2Photo.setImage(line2Photo.getImage().getScaledInstance(
				line0.getWidth(),line0.getHeight(), Image.SCALE_DEFAULT));
		line3Photo.setImage(line3Photo.getImage().getScaledInstance(
				line0.getWidth(),line0.getHeight(), Image.SCALE_DEFAULT));
		line4Photo.setImage(line4Photo.getImage().getScaledInstance(
				line0.getWidth(),line0.getHeight(), Image.SCALE_DEFAULT));
		line5Photo.setImage(line5Photo.getImage().getScaledInstance(
				line0.getWidth(),line0.getHeight(), Image.SCALE_DEFAULT));
		line6Photo.setImage(line6Photo.getImage().getScaledInstance(
				line0.getWidth(),line0.getHeight(), Image.SCALE_DEFAULT));
		line7Photo.setImage(line7Photo.getImage().getScaledInstance(
				line0.getWidth(),line0.getHeight(), Image.SCALE_DEFAULT));
		line8Photo.setImage(line8Photo.getImage().getScaledInstance(
				line0.getWidth(),line0.getHeight(), Image.SCALE_DEFAULT));
		smilePhoto.setImage(smilePhoto.getImage().getScaledInstance(
				smile.getWidth(),smile.getHeight(), Image.SCALE_DEFAULT));
		sadPhoto.setImage(sadPhoto.getImage().getScaledInstance(
				sad.getWidth(),sad.getHeight(), Image.SCALE_DEFAULT));
	}

	private void initJLabel() {
		proposal = new JTextArea();
		proposal.setEditable(false);
		proposal.append(" 情緒指數：" + value + " 分\n"
				+ " test\n");
		add(proposal);
		
		line0 = new JLabel();
		line0.setSize(getWidth() / 8, getHeight() / 17);
		line0.setIcon(line0Photo);
		add(line0);
		
		line1 = new JLabel();
		line1.setSize(getWidth() / 8, getHeight() / 17);
		line1.setIcon(line0Photo);
		add(line1);
		
		line2 = new JLabel();
		line2.setSize(getWidth() / 8, getHeight() / 17);
		line2.setIcon(line0Photo);
		add(line2);
		
		line3 = new JLabel();
		line3.setSize(getWidth() / 8, getHeight() / 17);
		line3.setIcon(line0Photo);
		add(line3);
		
		line4 = new JLabel();
		line4.setSize(getWidth() / 8, getHeight() / 17);
		line4.setIcon(line0Photo);
		add(line4);
		
		line5 = new JLabel();
		line5.setSize(getWidth() / 8, getHeight() / 17);
		line5.setIcon(line0Photo);
		add(line5);
		
		line6 = new JLabel();
		line6.setSize(getWidth() / 8, getHeight() / 17);
		line6.setIcon(line0Photo);
		add(line6);
		
		line7 = new JLabel();
		line7.setSize(getWidth() / 8, getHeight() / 17);
		line7.setIcon(line0Photo);
		add(line7);
		
		line8 = new JLabel();
		line8.setSize(getWidth() / 8, getHeight() / 17);
		line8.setIcon(line0Photo);
		add(line8);
		
		line9 = new JLabel();
		line9.setSize(getWidth() / 8, getHeight() / 17);
		line9.setIcon(line0Photo);
		add(line9);
		
		line10 = new JLabel();
		line10.setSize(getWidth() / 8, getHeight() / 17);
		line10.setIcon(line0Photo);
		add(line10);
		
		line11 = new JLabel();
		line11.setSize(getWidth() / 8, getHeight() / 17);
		line11.setIcon(line0Photo);
		add(line11);
		
		line12 = new JLabel();
		line12.setSize(getWidth() / 8, getHeight() / 17);
		line12.setIcon(line0Photo);
		add(line12);
		
		line13 = new JLabel();
		line13.setSize(getWidth() / 8, getHeight() / 17);
		line13.setIcon(line0Photo);
		add(line13);
		
		line14 = new JLabel();
		line14.setSize(getWidth() / 8, getHeight() / 17);
		line14.setIcon(line0Photo);
		add(line14);
		
		line15 = new JLabel();
		line15.setSize(getWidth() / 8, getHeight() / 17);
		line15.setIcon(line0Photo);
		add(line15);
		
		smile = new JLabel();
		smile.setSize(getWidth() / 10, getWidth() / 10);
		smile.setIcon(smilePhoto);
		add(smile);
		
		sad = new JLabel();
		sad.setSize(getWidth() / 10, getWidth() / 10);
		sad.setIcon(sadPhoto);
		add(sad);
	}

	private void initBound() {
		proposal.setBounds((getWidth() / 2) - (getWidth() / 5), 0,
				getWidth()*5/10 , getHeight());
	}

	private void initLocation() {
		line0.setLocation(getWidth()/8, 0);
		line1.setLocation(getWidth()/8, getHeight()*1/16);
		line2.setLocation(getWidth()/8, getHeight()*2/16);
		line3.setLocation(getWidth()/8, getHeight()*3/16);
		line4.setLocation(getWidth()/8, getHeight()*4/16);
		line5.setLocation(getWidth()/8, getHeight()*5/16);
		line6.setLocation(getWidth()/8, getHeight()*6/16);
		line7.setLocation(getWidth()/8, getHeight()*7/16);
		line8.setLocation(getWidth()/8, getHeight()*8/16);
		line9.setLocation(getWidth()/8, getHeight()*9/16);
		line10.setLocation(getWidth()/8, getHeight()*10/16);
		line11.setLocation(getWidth()/8, getHeight()*11/16);
		line12.setLocation(getWidth()/8, getHeight()*12/16);
		line13.setLocation(getWidth()/8, getHeight()*13/16);
		line14.setLocation(getWidth()/8, getHeight()*14/16);
		line15.setLocation(getWidth()/8, getHeight()*15/16);
		smile.setLocation(0, 0);
		sad.setLocation(0, getHeight()-(getWidth() / 10));
	}
	
	private void initLines() {
		for (int i = 0; i < value * 15 / 100; i++) {
			if (i == 0) {
				line15.setIcon(line8Photo);
			} else if (i == 1) {
				line14.setIcon(line8Photo);
			} else if (i == 2) {
				line13.setIcon(line7Photo);
			} else if (i == 3) {
				line12.setIcon(line7Photo);
			} else if (i == 4) {
				line11.setIcon(line6Photo);
			} else if (i == 5) {
				line10.setIcon(line6Photo);
			} else if (i == 6) {
				line9.setIcon(line5Photo);
			} else if (i == 7) {
				line8.setIcon(line5Photo);
			} else if (i == 8) {
				line7.setIcon(line4Photo);
			} else if (i == 9) {
				line6.setIcon(line4Photo);
			} else if (i == 10) {
				line5.setIcon(line3Photo);
			} else if (i == 11) {
				line4.setIcon(line3Photo);
			} else if (i == 12) {
				line3.setIcon(line2Photo);
			} else if (i == 13) {
				line2.setIcon(line2Photo);
			} else if (i == 14) {
				line1.setIcon(line1Photo);
			} else if (i == 15) {
				line0.setIcon(line1Photo);
			}
		}
	}

	// init Component end //

	// API//
	public void setValue(int value) {
		this.value = value;
		String temp = "";
		proposal.setText(" 情緒指數：" + value + " 分\n");
		if (value < 40) {
			temp = "強烈建議您與家人或朋友聊聊心事!\n" + "利用早晨或傍晚出去走走，曬曬陽光；\n"
					+ "另外提醒您盡量避免吃如巧克力、咖啡、茶等刺激性飲食";
		} else if (value < 60) {
			temp = "要不要找家人或朋友聊聊呢?\n" + "先看個影片轉換一下吧!";
		} else if (value < 80) {
			temp = "現在狀態尚可，有空要多出去戶外踏青、曬曬溫暖的陽光哦!\n";
		} else if (value <= 100) {
			temp = "目前的狀態很棒!\n" + "要繼續保持哦!";
		} 
		proposal.setText(" 情緒指數：" + value + " 分\n" + temp);
		initLines();
	}
	// API end //
}
