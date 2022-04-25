package pparticulars;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Main menu of picture-particulars.
 */
public class Menu {
    private static final String API_KEY = API.API_KEY; // replace with your OMDb API key
    private static CommandLine commandLine;

    /**
     * Main method of Menu.
     *
     * @param args command-line arguments passed in (--q specifies query, -p enables full plot)
     * @throws ParseException if cannot parse arguments
     */
    public static void main(String[] args) throws ParseException {
        Gson gson = new Gson();
        commandLine = initializeCommandLine(args);
        URL url = formURL(commandLine.getOptionValue("query"));

        try (InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(url).openStream())) {
            Movie movie = gson.fromJson(reader, Movie.class);
            movie.wordWrapPlot();
            System.out.println(movie);
        } catch (NullPointerException | JsonSyntaxException e) {
            System.out.println("Show or movie not found.");
        } catch (JsonParseException e) {
            System.out.println("An API error occurred.");
        } catch (IOException e) {
            System.out.println("Connection failed—make sure you are connected to the Internet.");
        }
    }

    /**
     * Parses a CommandLine from options and arguments.
     *
     * @param args command-line arguments passed in
     * @return CommandLine from options and arguments
     * @throws ParseException if cannot parse arguments
     */
    private static CommandLine initializeCommandLine(String[] args) throws ParseException {
        Options options = new Options();
        Option q = Option.builder()
                .required(true)
                .longOpt("query")
                .hasArg(true)
                .argName("queryText")
                .desc("Show or movie to find info for")
                .build();
        options.addOption(q)
                .addOption("p", false, "Enable full plot");
        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    /**
     * Generates the OMDb API URL from a movie or show title.
     *
     * @param query a movie or show title
     * @return the OMDb API URL
     */
    private static URL formURL(String query) {
        String urlString = "https://www.omdbapi.com/?apikey=" + API_KEY;
        urlString += "&t=" + query.replaceAll("\\s+", "+");
        if (commandLine.hasOption("p")) {
            urlString = urlString.concat("&plot=full");
        }

        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
