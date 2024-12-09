import org.jfree.data.xy.XYSeries;

import java.text.DecimalFormat;

/**
 * The salter.
 * Adds garbage to the data.
 */
public class Salter {
    private DataManager dataManager;
    private Displayer displayer;

    /**
     * Creates a Salter object.
     * Instantiates the DataManager and Displayer attributes.
     */
    public Salter() {
        this.dataManager = new DataManager();
        this.displayer = new Displayer();
    }

    /**
     * Salts the y values in the given file.
     * @param filename the name of the file.
     * @param start the start of the salt range.
     * @param end the end of the salt range.
     */
    public void salt(String filename, double start, double end) {
        XYSeries series = this.dataManager.readFromCSV(filename);
        DecimalFormat df = new DecimalFormat("#.##########");
        series.setDescription(series.getDescription() + String.format(",salt range: [" + df.format(start) + ";" +
                df.format(end) + "]"));

        for (int i = 0; i < series.getItemCount(); i++) {
            double x = series.getX(i).doubleValue();
            double y = series.getY(i).doubleValue();

            double saltedY = saltValue(y, start, end);
            series.updateByIndex(i, saltedY);
        }

        this.dataManager.saveToCSV("salted-" + filename, series);
        int index = filename.indexOf(".csv");
        String trueFilename = filename.substring(0, index);
        this.displayer.plotAndDisplay("salted-" + trueFilename, series, series.getDescription());
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

        salter.salt("extra.csv",  1, 1000);
    }
}
