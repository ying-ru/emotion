package accelerometer.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class CompassBearingGraphPanel extends JPanel {

    private BufferedImage buffImage = null;
    private double boundsWidth, boundsHeight;
    private double circleDiameter, circleRadius;
    private double xCenter, yCenter;
    private double xOld, yOld;
    private double xOut, yOut, zOut;
    private double compassBearing;
    private boolean compassAxesExist = false;
    private boolean Exist = false;
    private Ellipse2D.Double oneGCircle;

    public CompassBearingGraphPanel() {
        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Compass Bearing"));
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

            this.circleDiameter = this.boundsWidth / 2;
            this.circleRadius = this.circleDiameter / 2;

            this.oneGCircle = new Ellipse2D.Double(xCenter - circleRadius, yCenter - circleRadius, circleDiameter, circleDiameter);
        }

        Graphics2D g2d = (Graphics2D) g;
        Graphics2D drawer = this.buffImage.createGraphics();

        RenderingHints drawerHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawerHints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        drawer.setRenderingHints(drawerHints);

        drawer.clearRect(0, 0, (int) boundsWidth, (int) boundsHeight);

        drawer.setColor(Color.BLACK);
        drawer.draw(this.oneGCircle);

        drawer.drawString("N", (int) (xCenter - 4), (int) (yCenter - circleRadius - 6));
        drawer.drawString("E", (int) (xCenter) + (int) (circleRadius + 6), (int) (yCenter + 4));
        drawer.drawString("S", (int) (xCenter - 4), (int) (yCenter + circleRadius + 16));
        drawer.drawString("W", (int) (xCenter - circleRadius - 16), (int) (yCenter + 6));

        for (i = 0; i < 360; i += 10) {
            int tickSize = 4;
            if (i == 0 || i == 90 || i == 180 || i == 270) {
                tickSize = 72;
            }

            drawer.draw(new Line2D.Double(
                    ((circleRadius + tickSize) * Math.cos(i * (Math.PI / 180.0)) + xCenter),
                    (-(circleRadius + tickSize) * Math.sin(i * (Math.PI / 180.0)) + yCenter),
                    ((circleRadius - tickSize) * Math.cos(i * (Math.PI / 180.0)) + xCenter),
                    (-(circleRadius - tickSize) * Math.sin(i * (Math.PI / 180.0)) + yCenter)));
        }

        drawer.setColor(Color.RED);

        drawer.draw(new Ellipse2D.Double(
                ((circleRadius * Math.cos((-compassBearing + 90) * (Math.PI / 180.0)) + xCenter) - 2),
                ((-circleRadius * Math.sin((-compassBearing + 90) * (Math.PI / 180.0)) + yCenter) - 2),
                4,
                4));
        drawer.fillOval(
                (int) ((circleRadius * Math.cos((-compassBearing + 90) * (Math.PI / 180.0)) + xCenter) - 2),
                (int) ((-circleRadius * Math.sin((-compassBearing + 90) * (Math.PI / 180.0)) + yCenter) - 2),
                4,
                4);

        g2d.drawImage(buffImage, null, 1, 1);
    }

    public double getCompassBearing() {
        return compassBearing;
    }

    public void setCompassBearing(double compassBearing) {
        this.compassBearing = compassBearing;
    }

    public boolean isExist() {
        return Exist;
    }

    public void setExist(boolean Exist) {
        this.Exist = Exist;
    }
}
