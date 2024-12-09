import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Plots the provided data.
 */
public class Displayer {
    /**
     * Displays the chart in a JFrame.
     * @param chart the chart to display.
     */
    public void displayChart(JFreeChart chart) {
        // create a panel for the chart
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(800, 600));

        // create a frame to display the chart
        JFrame frame = new JFrame(chart.getTitle().getText());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Saves the chart to a PNG file.
     * @param filename the name of the file.
     * @param chart the chart to save.
     */
    public void saveChartAsPNG(String filename, JFreeChart chart) {
        try {
            File file = new File(filename);
            ImageIO.write(chart.createBufferedImage(800, 600), "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates and displays the plot for the given series and description.
     * @param filename the name of the file.
     * @param series the data series to plot.
     * @param description the description of the plot.
     */
    public void plotAndDisplay(String filename, XYSeries series, String description) {
        // create the chart
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                description, "X", "Y", dataset,
                PlotOrientation.VERTICAL, true, true, false
        );

        // display and save in a file
        displayChart(chart);
        saveChartAsPNG(filename + ".png", chart);
    }
}