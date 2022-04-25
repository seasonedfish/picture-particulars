/*
 * MIT License
 *
 * Copyright (c) 2020 Fisher Sun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package pparticulars;

import com.google.gson.annotations.SerializedName;

/**
 * A class to represent a show or movie.
 *
 * @author Fisher
 */
public class Movie {
    // Fields must unfortunately be capitalized to match the OMDb API json
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
     *
     * @author Fisher
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
