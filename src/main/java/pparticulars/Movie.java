package pparticulars;

/**
 * A class to represent a show or movie.
 *
 * @author Fisher
 */
public class Movie {
    // Fields must unfortunately be capitalized to match the OMDb API json
    private String Title;
    private String Year;
    private String Type;
    private String Rated;
    private String Genre;
    private String Plot;
    private String imdbID;
    private Rating[] Ratings;

    public Movie(String title, String year, String type, String rated,
                 String genre, String plot, String imdb, Rating[] ratings) {
        Title = title;
        Year = year;
        Type = type;
        Rated = rated;
        Genre = genre;
        Plot = plot;
        imdbID = imdb;
        Ratings = ratings;
    }

    /**
     * Wraps words of the plot so that no line is wider than <code>WRAP_LENGTH</code>.
     */
    public void wordWrapPlot() {
        final int WRAP_LENGTH = 60;
        String wrapped = "";
        String line;
        int lineStart = -1;
        int lineEnd;

        if (Plot == null || !Plot.contains(" ")) {
            Plot = "\nNo plot summary available.";
        }
        if (Plot.length() > WRAP_LENGTH) {
            // concatenates all lines besides last line
            do {
                lineEnd = Plot.lastIndexOf(" ", lineStart + WRAP_LENGTH);
                line = Plot.substring(lineStart + 1, lineEnd); // skips the space
                wrapped = String.join("\n", wrapped, line);
                lineStart = lineEnd;
            } while (lineEnd != Plot.lastIndexOf(" "));

            // concatenates last line
            line = Plot.substring(lineStart);
            wrapped = wrapped.concat(line);

            Plot = wrapped;
        }
    }

    public String getImdbID() {
        return imdbID;
    }

    @Override
    public String toString() {
        String returnString = Title + " (" + Year + " " + Type + ")"
                + "\n" + "Rated: " + Rated
                + "\n" + "Genre: " + Genre
                + "\n" + Plot
                + "\n";

        for (Rating r : Ratings) {
            returnString = String.join("\n", returnString, r.toString());
        }
        return returnString;
    }

    /**
     * A class to represent a show or movie's rating.
     *
     * @author Fisher
     */
    static class Rating {
        private String Source;
        private String Value;

        Rating(String source, String value) {
            Source = source;
            Value = value;
        }

        @Override
        public String toString() {
            return Source + ": " + Value;
        }
    }
}
