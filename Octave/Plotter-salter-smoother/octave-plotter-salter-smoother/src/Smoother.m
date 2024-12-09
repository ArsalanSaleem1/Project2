function Smoother(filename, window)
    % The data files are located in this folder
    input_folder = '../generated-data';
    output_folder = '../generated-data';

    % Create the CSV filename - add the extension to the given filename
    csv_filename = fullfile(input_folder, strcat(filename, '.csv'));

    % Open the CSV file for reading
    fid = fopen(csv_filename, 'r');
    if fid == -1
        error('File %s not found.', csv_filename);
    end

    % Read and discard the header line
    header_line = fgetl(fid);

    % Read the x and y values
    data = textscan(fid, '%f%f', 'Delimiter', ',');
    fclose(fid);

    % Save x and y values in respective variables
    x = data{1};
    y = data{2};

    % Check for sufficient data
    if length(y) < window
        error("Insufficient data!");
    end

    % Apply smoothing
    smoothed_y = zeros(size(y));
    half_window = floor(window / 2);
    n = length(y);

    # Calculate the average and replace the y value
    for i = 1:n
        start_idx = max(1, i - half_window);
        end_idx = min(n, i + half_window);
        smoothed_y(i) = mean(y(start_idx:end_idx));
    end

    % Add the smooth window to the header
    new_header = strcat(header_line, sprintf(',smooth window = %d', window));

    % Create the new filename for the smoothed data
    smoother_csv_filename = fullfile(output_folder, strcat('smoothed-', filename, '.csv'));

    % Save the smoothed data to a new CSV file
    fid = fopen(smoother_csv_filename, 'w');
    if fid == -1
        error('Unable to open file %s for writing.', smoother_csv_filename);
    end

    fprintf(fid, '%s\n', new_header); % Write the updated header
    for i = 1:length(x)
        fprintf(fid, '%.6f,%.6f\n', x(i), smoothed_y(i));
    end
    fclose(fid);

    % Plot the smoothed data
    figure;
    plot(x, smoothed_y, 'r'); % Red line for smoothed data
    title(sprintf('Smoothed Data for %s.csv (Window: %d)', filename, window));
    xlabel('x');
    ylabel('Smoothed y');

    % Save the plot as a PNG file
    smoother_plot_filename = fullfile(output_folder, strcat('smoothed-', filename, '.png'));
    saveas(gcf, smoother_plot_filename);

    % Let the user know that the files have been generated
    fprintf('Done!');
end
