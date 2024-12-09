import org.apache.commons.math4.legacy.stat.descriptive.DescriptiveStatistics;
import org.jfree.data.xy.XYSeries;

/**
 * Smooths salted data.
 */
public class Smoother {
    private DataManager dataManager;
    private Displayer displayer;

    /**
     * Creates a Smoother object.
     * Instantiates the DataManager and Displayer attributes.
     */
    public Smoother() {
        this.dataManager = new DataManager();
        this.displayer = new Displayer();
    }

    /**
     * Smooths the y values in the given file.
     * @param filename the name of the file.
     * @param window the window value.
     */
    public void smooth(String filename, int window) {
        XYSeries series = this.dataManager.readFromCSV(filename);
        series.setDescription(series.getDescription() + ",smooth window = " + window);

        // is used for calculating moving average
        DescriptiveStatistics stats = new DescriptiveStatistics();

        // for each y value in the series
        for (int i = 0; i < series.getItemCount(); i++) {
            // clear the previous values
            stats.clear();

            int leftStart = Math.max(i - (window / 2), 0);
            int rightEnd = Math.min(series.getItemCount() - 1, i + (window / 2));

            // add neighbors that are within the window range
            for (int j = leftStart; j <= rightEnd; j++) {
                // don't use the current y value itself, only use its neighbors
                if (j != i) {
                    double y = series.getY(j).doubleValue();
                    stats.addValue(y);
                }
            }

            // the smoothed value is the average of the neighbors
            double smoothedY = stats.getMean();
            // replace the original y value with the smoothed value
            series.updateByIndex(i, smoothedY);
        }

        this.dataManager.saveToCSV("smoothed-" + filename, series);
        int index = filename.indexOf(".csv");
        String trueFilename = filename.substring(0, index);
        this.displayer.plotAndDisplay("smoothed-" + trueFilename, series, series.getDescription());
    }

    /**
     * The driver method.
     * @param args is not used.
     */
    public static void main(String[] args) {
        /*Smoother smoother = new Smoother();
        smoother.smooth("salted-extra.csv", 200);*/

        Plotter plotter = new Plotter();
        plotter.generate("more", (x) -> 5 * x * x + 2 * x + 20,
                -10, 10, 0.01, "y = 5x^2 + 2x + 20");

        Salter salter = new Salter();
        salter.salt("more.csv",  1, 500);

        Smoother smoother = new Smoother();
        smoother.smooth("salted-more.csv", 2000);
    }
}
