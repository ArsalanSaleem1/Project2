import org.jfree.data.xy.XYSeries;

/**
 * Generates the chart data, saves it in an CSV file,
 * creates and displays a chart, and saves it in a PNG file.
 */
public class Plotter {

    private DataManager dataManager;
    private Displayer displayer;

    /**
     * Creates a Plotter object.
     * Instantiates the DataManager and Displayer attributes.
     */
    public Plotter() {
        this.dataManager = new DataManager();
        this.displayer = new Displayer();
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
        // create an empty series
        String header = "x,y," + description;
        XYSeries series = new XYSeries(header);
        series.setDescription(header);

        // generate data for the given range of x values
        for (double x = start; x <= end; x += increment) {
            double y = function.calculate(x);
            series.add(x, y);
        }

        // save data to an CSV file, and display the plot
        this.dataManager.saveToCSV(filename + ".csv", series);
        this.displayer.plotAndDisplay(filename, series, series.getDescription());
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

        plotter.generate("extra", (x) -> 5 * x * x + 2 * x + 20,
                -10, 10, 0.01, "y = 5x^2 + 2x + 20");
    }
}