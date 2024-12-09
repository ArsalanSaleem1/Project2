import java.io.IOException;
import java.io.PrintWriter;

/**
 * Plots the output in csv.
 */
public class Plotter {
    /**
     * Default values.
     */
    private static final String DEFAULT_FILENAME = "data.csv";
    private static final FunctionToPlot DEFAULT_FUNCTION = (x) -> Math.sin(x);
    private static final String DEFAULT_DESCRIPTION = "y = sin(x)";
    private static final double DEFAULT_START = -10.0;
    private static final double DEFAULT_END = 10.0;
    private static final double DEFAULT_INCREMENT = 0.1;

    /**
     * Uses default file and function.
     */
    public void generate() {
        generate(DEFAULT_FILENAME, DEFAULT_FUNCTION,
                DEFAULT_START, DEFAULT_END, DEFAULT_INCREMENT, DEFAULT_DESCRIPTION);
    }

    /**
     * Stores the x and y values in the given file.
     * @param filename the name of the file.
     * @param function the function to calculate y based on x.
     * @param start the starting x.
     * @param end the ending x.
     * @param increment the increment for the x values.
     */
    public void generate(String filename, FunctionToPlot function,
                     double start, double end, double increment, String description) {
        // try to open the file
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println("x,y," + description);
            // write the x and y values
            for (double i = start; i <= end; i += increment) {
                writer.println(i + "," + function.calculate(i));
            }
        } catch (IOException e) {
            // the file could not be written in
            e.printStackTrace();
        }
    }

    /**
     * The function that is plotted.
     * Can be passed as an argument to the plot method.
     */
    @FunctionalInterface
    public interface FunctionToPlot {
        /**
         * Returns the y value.
         * @param x the x value.
         * @return the y value.
         */
        double calculate(double x);
    }

    /**
     * Driver method.
     * @param args not used.
     */
    public static void main(String[] args) {
        Plotter plotter = new Plotter();

        // the first file
        plotter.generate("test.csv", (x) -> 3 * x +
             Math.cos(2 * x), 0, 100, 0.01, "y = 3x + cos(2x)");
        // the second file
        plotter.generate();
        // the third file
        plotter.generate("extra.csv", (x) -> 5 * x * x + 2 * x + 20,
                -100, 100, 0.01, "y = 5x^2 + 2x + 20");
    }
}