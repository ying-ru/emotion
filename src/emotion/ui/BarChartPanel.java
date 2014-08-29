package emotion.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.StandardGradientPaintTransformer;

public class BarChartPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private DefaultCategoryDataset defaultcategorydataset;
	private JFreeChart jfreechart;
    class CustomBarRenderer extends BarRenderer {   
        private static final long serialVersionUID = 1L;   
        private Paint colors[];   
        
        public Paint getItemPaint(int i, int j) {   
            return colors[j % colors.length];   
        }   
        
        public CustomBarRenderer(Paint apaint[]) {   
            colors = apaint;   
        }   
    }
    
    {
    	ChartFactory.setChartTheme(StandardChartTheme.createDarknessTheme());
    }
    
    public BarChartPanel() {
        defaultcategorydataset = new DefaultCategoryDataset();
    }   
   
    private CategoryDataset createDataset() {   
//        defaultcategorydataset.addValue(40100D, "", "High Alpha");   
//        defaultcategorydataset.addValue(68000D, "", "Low Alpha");   
//        defaultcategorydataset.addValue(41000D, "", "High Beta");   
//        defaultcategorydataset.addValue(68000D, "", "Low Beta");   
//        defaultcategorydataset.addValue(53000D, "", "High Gamma");
//        defaultcategorydataset.addValue(53000D, "", "Low Gamma"); 
        return defaultcategorydataset;   
    }   
    
    private JFreeChart createChart(CategoryDataset categorydataset) {   
        jfreechart = ChartFactory.createBarChart("腦波強度", null, null, categorydataset, PlotOrientation.VERTICAL, false, false, false);   
        TextTitle texttitle = jfreechart.getTitle();
        texttitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        texttitle.setBorder(0.0D, 0.0D, 1.0D, 0.0D);
        
        CategoryPlot categoryplot = (CategoryPlot)jfreechart.getPlot();   
        categoryplot.setNoDataMessage("NO DATA!");   
        categoryplot.setBackgroundPaint(null);   
        categoryplot.setInsets(new RectangleInsets(10D, 5D, 5D, 5D));   
        categoryplot.setOutlinePaint(Color.red);   
        categoryplot.setRangeGridlinePaint(Color.white);   
        categoryplot.setRangeGridlineStroke(new BasicStroke(0.0F));
        
        categoryplot.getDomainAxis().setTickLabelFont(new Font("sansserf", Font.PLAIN, 25));
        
        
        Paint apaint[] = createPaint();
        CustomBarRenderer custombarrenderer = new CustomBarRenderer(apaint);
        custombarrenderer.setShadowVisible(false);
        custombarrenderer.setBaseItemLabelsVisible(false);
        custombarrenderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));   
        categoryplot.setRenderer(custombarrenderer);   
        
        NumberAxis numberaxis = (NumberAxis)categoryplot.getRangeAxis();  
        numberaxis.setRange(0.0D, 150000D);
        numberaxis.setVisible(false);
        return jfreechart;   
    }
    
    private Paint[] createPaint() {   
        Paint apaint[] = new Paint[6];   
        apaint[0] = new GradientPaint(0.0F, 0.0F, new Color(230, 80, 80, 150), 0.0F, 0.0F, new Color(230, 80, 80, 150));
        apaint[1] = new GradientPaint(0.0F, 0.0F, new Color(230, 150, 80, 150), 0.0F, 0.0F, new Color(230, 150, 80, 150));   
        apaint[2] = new GradientPaint(0.0F, 0.0F, new Color(230, 200, 80, 150), 0.0F, 0.0F, new Color(230, 200, 80, 150));  
        apaint[3] = new GradientPaint(0.0F, 0.0F, new Color(90, 230, 130, 150), 0.0F, 0.0F, new Color(90, 230, 130, 150));
        apaint[4] = new GradientPaint(0.0F, 0.0F, new Color(50, 80, 190, 150), 0.0F, 0.0F, new Color(50, 80, 190, 150));   
        apaint[5] = new GradientPaint(0.0F, 0.0F, new Color(130, 50, 190, 150), 0.0F, 0.0F, new Color(130, 50, 190, 150));  
        return apaint;   
    }
    
    public JPanel createDemoPanel() {   
        JFreeChart jfreechart = createChart(createDataset());   
        return new ChartPanel(jfreechart);   
    }
    
    public void setTitle(String s) {
    	jfreechart.setTitle(s);
    }
    
    public void setRange(double d) {
    	jfreechart.getCategoryPlot().getRangeAxis().setRange(0.0D, d);
    }
    
    public void setValueVisible(boolean b) {
    	jfreechart.getCategoryPlot().getRangeAxis().setVisible(b);
    }
    
    public void setTickLabelsVisible(boolean b) {
    	jfreechart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(b);
    }
    
    public DefaultCategoryDataset getDefaultCategoryDataset() {
    	return defaultcategorydataset;
    }
    
    public void updateValue(double value, String Name) {
        getDefaultCategoryDataset().setValue(value, "", Name);
    }
}
