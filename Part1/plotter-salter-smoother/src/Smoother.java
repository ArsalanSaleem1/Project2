import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Smooths salted data.
 */
public class Smoother {
    /**
     * Smooths the y values in the given file.
     * @param filename the name of the file.
     * @param window the window value.
     */
    public void smooth(String filename, int window) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            List<Double> xValues = new ArrayList<>();
            List<Double> yValues = new ArrayList<>();

            String header = scanner.nextLine() + ",smooth window = " + window;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                double x = Double.parseDouble(data[0]);
                double y = Double.parseDouble(data[1]);

                xValues.add(x);
                yValues.add(y);
            }

            List<Double> smoothedYValues = smooth(yValues, window);
            createDataFile("smoothed-" + filename,
                    xValues, smoothedYValues, header);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the smoothed y values.
     * @param yValues original y values.
     * @param window the window value.
     * @return the smoothed y values.
     */
    private List<Double> smooth(List<Double> yValues, int window) {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < yValues.size(); i++) {
            double smoothedY = smooth(yValues, i, window);
            result.add(smoothedY);
        }

        return result;
    }

    /**
     * Returns the smoothed y value for the given index.
     * @param yValues original y values.
     * @param i the index of the current y value.
     * @param window the window value.
     * @return the smoothed y value.
     */
    private double smooth(List<Double> yValues, int i, int window) {
        // the sum of all neighboring values
        double sum = 0;

        // counts the existing neighboring values
        int count = 0;
        // the range of the possible neighbors
        int leftStart = Math.max(i - (window / 2), 0);
        int rightEnd = Math.min(yValues.size() - 1, i + (window / 2));

        // loop over all neighbors
        for (int j = leftStart; j <= rightEnd ; j++) {
            // don't use the current y value itself, only use its neighbors
            if (j != i) {
                // update the sum
                sum += yValues.get(j);
                // increment the count of neighbors
                count++;
            }
        }

        // if there are no neighbors
        if (count == 0) {
            // the sum is the original y value itself
            sum = yValues.get(i);
            count = 1;
        }

        // calculate the average
        return sum / count;
    }


    /**
     * Creates the salted file.
     * @param filename the name of the file.
     * @param x x values.
     * @param y y values.
     * @param header the header.
     */
    private void createDataFile(String filename, List<Double> x, List<Double> y, String header) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println(header);

            for (int i = 0; i < x.size(); i++) {
                writer.println(x.get(i) + "," + y.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Smoother smoother = new Smoother();
        Salter salter = new Salter();

        salter.salt("extra.csv", 1, 100);
        smoother.smooth("salted-extra.csv", 20);
    }
}
