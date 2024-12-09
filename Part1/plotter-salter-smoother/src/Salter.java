import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The salter.
 * Adds garbage to the data.
 */
public class Salter {
    /**
     * Salts the y values in the given file.
     * @param filename the name of the file.
     * @param start the start of the salt range.
     * @param end the end of the salt range.
     */
    public void salt(String filename, double start, double end) {
        String header;
        // create empty lists to store x and y values
        List<Double> xValues = new ArrayList<>();
        List<Double> saltedYValues = new ArrayList<>();

        // open the file for reading
        try (Scanner scanner = new Scanner(new File(filename))) {
            // the header in the salted file should include the range of the salt
            DecimalFormat df = new DecimalFormat("#.##########");
            header = scanner.nextLine() + String.format(",salt range: [" + df.format(start) + ";" +
                    df.format(end) + "]");

            // read each data line
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                // get the x and y values
                double x = Double.parseDouble(data[0]);
                double y = Double.parseDouble(data[1]);
                // salt the y value
                double saltedY = saltValue(y, start, end);

                // add the x and y values to their respective lists
                xValues.add(x);
                saltedYValues.add(saltedY);
            }

            // writes the x and y values into a new file
            createDataFile("salted-" + filename, xValues, saltedYValues, header);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * Salts the given y value.
     * @param y y value.
     * @param start the start of the salt range.
     * @param end the end of the salt range.
     * @return the salted value.
     */
    private double saltValue(double y, double start, double end) {
        double dice = randomInRange(start, end);
        if (randomInRange(0, 1) == 1) {
            dice *= -1;
        }

        return y + dice;
    }

    /**
     * Generates a random number in the given range.
     * @param start the start of the salt range.
     * @param end the end of the salt range.
     * @return the random number.
     */
    private static double randomInRange(double start, double end) {
        return (Math.random() * (end - start + 1) + start);
    }

    /**
     * Driver method.
     * @param args not used.
     */
    public static void main(String[] args) {
        Salter salter = new Salter();

        salter.salt("extra.csv",  1, 50);
    }
}