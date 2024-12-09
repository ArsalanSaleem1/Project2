function Salter(filename, saltStart, saltEnd)
    % The data files are in this folder
    input_folder = '../generated-data';

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

    % Generate salted Y values
    random_salts = saltStart + (rand(size(y)) * (saltEnd - saltStart));
    random_salts = random_salts + (rand(size(y)) < eps);
    % Determine whether to add or subtract the salt value
    signs = randi([0, 1], size(y)) * 2 - 1;
    saltedY = y + random_salts .* signs;

    % Add the salt range to the header
    new_header = strcat(header_line, sprintf(',salt range: [%d, %d]', saltStart, saltEnd));

    % Create the new filename for the salted data
    salted_csv_filename = fullfile(input_folder, strcat('salted-', filename, '.csv'));

    % Save the salted data to a new CSV file
    fid = fopen(salted_csv_filename, 'w');
    if fid == -1
        error('Unable to open file %s for writing.', salted_csv_filename);
    end

    fprintf(fid, '%s\n', new_header); % Write the updated header
    for i = 1:length(x)
        fprintf(fid, '%.6f,%.6f\n', x(i), saltedY(i));
    end
    fclose(fid);

    % Plot the salted data
    figure;
    plot(x, saltedY, 'r'); % Red line for salted data
    title(sprintf('Salted Data for %s.csv (Range: [%d, %d])', filename, saltStart, saltEnd));

    xlabel('x');
    ylabel('Salted y');

    % Save the plot as a PNG file
    salted_plot_filename = fullfile(input_folder, strcat('salted-', filename, '.png'));
    saveas(gcf, salted_plot_filename);

    % Let the user done the salted data and plot have been generated
    fprintf('Done!');
end
