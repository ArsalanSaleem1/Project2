import org.jfree.data.xy.XYSeries;
import java.io.*;
import java.util.Scanner;

/**
 * Saves the chart data into a CSV file.
 * Retrieves the chart data from a CSV file.
 */
public class DataManager {
    /**
     * Saves the XYSeries data to a CSV file.
     * @param filename the file to save the data.
     * @param series the series containing the data.
     */
    public void saveToCSV(String filename, XYSeries series) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println(series.getDescription());

            for (int i = 0; i < series.getItemCount(); i++) {
                writer.println(series.getX(i) + "," + series.getY(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads CSV data from a file and returns an XYSeries.
     * @param filename the file to read data from.
     * @return the XYSeries.
     */
    public XYSeries readFromCSV(String filename) {
        XYSeries series = new XYSeries("Data");
        try (Scanner scanner = new Scanner(new File(filename))) {
            String header = scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                double x = Double.parseDouble(data[0]);
                double y = Double.parseDouble(data[1]);
                series.add(x, y);
            }

            series.setDescription(header);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return series;
    }
}