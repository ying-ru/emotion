package accelerometer.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class GyroGraphPanel extends JPanel {

    private BufferedImage buffImage = null;
    private double boundsWidth, boundsHeight;
    private double[] circleDiameter = {0.0, 0.0, 0.0};
    private double[] circleRadius = {0.0, 0.0, 0.0};
    private double xCenter, yCenter;
    private double xOld, yOld;
    private double xOut, yOut, zOut;
    private boolean Exist = false;
    private Ellipse2D.Double[] oneGCircle = {
        new Ellipse2D.Double(),
        new Ellipse2D.Double(),
        new Ellipse2D.Double()};
    private Color[] circlePen = {Color.WHITE, Color.WHITE, Color.WHITE};

    public GyroGraphPanel() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Gyroscope Headings"));
        this.setPreferredSize(new Dimension(317, 317));
    }

    @Override
    public void paintComponent(Graphics g) {
        int i;

        super.paintComponent(g);

        if (this.buffImage == null) {
            this.buffImage = (BufferedImage) this.createImage((int) (this.getBounds().getWidth() - 2), (int) (this.getBounds().getHeight() - 2));

            this.boundsWidth = this.buffImage.getWidth();
            this.boundsHeight = this.buffImage.getHeight();

            this.xCenter = this.boundsWidth / 2;
            this.yCenter = this.boundsHeight / 2;

            circlePen[0] = Color.RED;
            circlePen[1] = Color.GREEN;
            circlePen[2] = Color.BLUE;

            this.circleDiameter[0] = this.boundsHeight / 1.5;
            this.circleDiameter[1] = this.boundsHeight / 1.6;
            this.circleDiameter[2] = this.boundsHeight / 1.714;

            for (i = 0; i < 3; i++) {
                this.circleRadius[i] = circleDiameter[i] / 2.0;
                this.oneGCircle[i] = new Ellipse2D.Double(xCenter - circleRadius[i], yCenter - circleRadius[i],
                        circleDiameter[i], circleDiameter[i]);
            }
        }

        Graphics2D g2d = (Graphics2D) g;
        Graphics2D drawer = this.buffImage.createGraphics();

        RenderingHints drawerHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawerHints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        drawer.setRenderingHints(drawerHints);

        drawer.clearRect(0, 0, (int) boundsWidth, (int) boundsHeight);

        for (i = 0; i < 3; i++) {
            drawer.setColor(Color.BLACK);
            drawer.draw(this.oneGCircle[i]);
        }

        drawer.setColor(Color.RED);
        drawer.draw(new Ellipse2D.Double(
                ((circleRadius[0] * Math.cos(xOut * (Math.PI / 180.0)) + xCenter) - 2),
                ((-circleRadius[0] * Math.sin(xOut * (Math.PI / 180.0)) + yCenter) - 2),
                4,
                4));
        drawer.fillOval((int) ((circleRadius[0] * Math.cos(xOut * (Math.PI / 180.0)) + xCenter) - 2),
                (int) ((-circleRadius[0] * Math.sin(xOut * (Math.PI / 180.0)) + yCenter) - 2),
                4,
                4);

        drawer.setColor(Color.GREEN);
        drawer.draw(new Ellipse2D.Double(
                ((circleRadius[1] * Math.cos(yOut * (Math.PI / 180.0)) + xCenter) - 2),
                ((-circleRadius[1] * Math.sin(yOut * (Math.PI / 180.0)) + yCenter) - 2),
                4,
                4));
        drawer.fillOval((int) ((circleRadius[1] * Math.cos(yOut * (Math.PI / 180.0)) + xCenter) - 2),
                (int) ((-circleRadius[1] * Math.sin(yOut * (Math.PI / 180.0)) + yCenter) - 2),
                4,
                4);

        drawer.setColor(Color.BLUE);
        drawer.draw(new Ellipse2D.Double(
                ((circleRadius[2] * Math.cos(zOut * (Math.PI / 180.0)) + xCenter) - 2),
                ((-circleRadius[2] * Math.sin(zOut * (Math.PI / 180.0)) + yCenter) - 2),
                4,
                4));
        drawer.fillOval(
                (int) ((circleRadius[2] * Math.cos(zOut * (Math.PI / 180.0)) + xCenter) - 2),
                (int) ((-circleRadius[2] * Math.sin(zOut * (Math.PI / 180.0)) + yCenter) - 2),
                4,
                4);
        g2d.drawImage(buffImage, null, 1, 1);
    }

    public double getXOut() {
        return xOut;
    }

    public void setXOut(double xOut) {
        this.xOut = xOut;
    }

    public double getYOut() {
        return yOut;
    }

    public void setYOut(double yOut) {
        this.yOut = yOut;
    }

    public double getZOut() {
        return zOut;
    }

    public void setZOut(double zOut) {
        this.zOut = zOut;
    }

    public boolean isExist() {
        return Exist;
    }

    public void setExist(boolean Exist) {
        this.Exist = Exist;
    }
}
