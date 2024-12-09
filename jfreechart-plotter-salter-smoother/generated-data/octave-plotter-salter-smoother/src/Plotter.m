function Plotter(filename, startX, endX, increment, description)
  % The csv and png files should be placed in this folder
  output_folder = '../generated-data';

  % If the output folder should be created, if it doesn't exist
  if ~exist(output_folder, 'dir')
    mkdir(output_folder);
  end

  %Generate the X values within the given range and with the provided increment
  x = startX:increment:endX;

  % Calculate Y values using the following function: y = 5x^2 + 2x + 20
  y = 5*x.^2 + 2*x + 20;

  % Plot the graph
  plot(x, y);

  % Place the given description above the graph
  title(description);

  % Create a PNG file with the graph and save it in the output folder
  plot_filename = fullfile(output_folder, strcat(filename, '.png'));
  saveas(gcf, plot_filename);

  % Create a name for the CSV file: just append the CSV extension to the given filename
  csv_filename = fullfile(output_folder, strcat(filename, '.csv'));

  % Open the CSV file for writing
  fid = fopen(csv_filename, 'w'); % Open the CSV file for writing

  % The first line in the CSV file is the header: x, y, description
  fprintf(fid, '%s,%s,%s\n', 'x', 'y', description);

  % For each x-y pair, write it to the CSV file
  for i = 1:length(x)
    fprintf(fid, '%.6f,%.6f\n', x(i), y(i));
  end

  % Close the generated CSV file
  fclose(fid);

  % Let the user know that the files have been generated
  fprintf('Done!');
end
