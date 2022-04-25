package pparticulars;

import com.google.gson.annotations.SerializedName;

/**
 * A class to represent a show or movie.
 */
public class Movie {
    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @SerializedName("Type")
    private String type;
    @SerializedName("Rated")
    private String rated;
    @SerializedName("Genre")
    private String genre;
    @SerializedName("Plot")
    private String plot;
    private String imdbID;
    @SerializedName("Ratings")
    private Rating[] ratings;

    public Movie(String title, String year, String type, String rated,
                 String genre, String plot, String imdb, Rating[] ratings) {
        this.title = title;
        this.year = year;
        this.type = type;
        this.rated = rated;
        this.genre = genre;
        this.plot = plot;
        imdbID = imdb;
        this.ratings = ratings;
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

        if (plot == null || !plot.contains(" ")) {
            plot = "\nNo plot summary available.";
        }
        if (plot.length() > WRAP_LENGTH) {
            // concatenates all lines besides last line
            do {
                lineEnd = plot.lastIndexOf(" ", lineStart + WRAP_LENGTH);
                line = plot.substring(lineStart + 1, lineEnd); // skips the space
                wrapped = String.join("\n", wrapped, line);
                lineStart = lineEnd;
            } while (lineEnd != plot.lastIndexOf(" "));

            // concatenates last line
            line = plot.substring(lineStart);
            wrapped = wrapped.concat(line);

            plot = wrapped;
        }
    }

    public String getImdbID() {
        return imdbID;
    }

    @Override
    public String toString() {
        String returnString = title + " (" + year + " " + type + ")"
                + "\n" + "Rated: " + rated
                + "\n" + "Genre: " + genre
                + "\n" + plot
                + "\n";

        for (Rating r : ratings) {
            returnString = String.join("\n", returnString, r.toString());
        }
        return returnString;
    }

    /**
     * A class to represent a show or movie's rating.
     */
    static class Rating {
        @SerializedName("Source")
        private String source;
        @SerializedName("Value")
        private String value;

        Rating(String source, String value) {
            this.source = source;
            this.value = value;
        }

        @Override
        public String toString() {
            return source + ": " + value;
        }
    }
}
