package com.test;


import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;

/**
 * A simple demonstration application showing how to create a line chart using data from an
 * {@link XYDataset}.
 *
 */
public class LineChart extends JFrame {
	
	private JFreeChart chart=null;
	private Color color;
	private int db;
	private String fileName;
	private List<XYDataItem> fromFileList;
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     */
    public LineChart(final String title,int db_, String file, List<XYDataItem> asd) {
        super(title);
        fromFileList = asd;
        fileName=file;
        db=db_;
        color =Color.GREEN;
        
        
        final XYDataset dataset = createDataset();
        chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
   
    }
    
  public void setColor(Color color_){
      color = color_;
      
      final XYDataset dataset = createDataset();
      chart = createChart(dataset);
      final ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
      setContentPane(chartPanel);
  }

    
  public JFreeChart getChart(){
        return chart;
  }
    
    private XYDataset createDataset() {
        
    	final XYSeries series1 = new XYSeries("First");
    	
    	if(fromFileList== null){
	        series1.add(1.0, 1.0);
	        series1.add(2.0, 4.0);
	        series1.add(3.0, 3.0);
	        series1.add(4.0, 5.0);
	        series1.add(5.0, 5.0);
	        series1.add(6.0, 7.0);
	        series1.add(7.0, 7.0);
	        series1.add(8.0, 8.0);

    	}else{
    		for(int i=0;i<fromFileList.size();i++){
    			series1.add(fromFileList.get(i).getX(),fromFileList.get(i).getY());
    		}
    	}
    	final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);

                
        /******/
        JSONObject obj = new JSONObject();
        obj.put("Name", "LineChart");
 
        Gson gson = new Gson();
        String numbersJSON = gson.toJson(series1.getItems());
        obj.put("DataList",numbersJSON);
 
        FileWriter file = null;
		try {
			file = new FileWriter(fileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
 
        } catch (IOException e) {
            e.printStackTrace();
 
        } finally {
            try {
				file.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        /*******/
        
        return dataset;
        
    }
    
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
         chart = ChartFactory.createXYLineChart(
            "Line Chart Demo 6",      // chart title
            "X",                      // x axis label
            "Y",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        

        plot.getRenderer().setSeriesPaint(0, color);
        

        
        return chart;
        
    }
    
    
    
    
   
}
