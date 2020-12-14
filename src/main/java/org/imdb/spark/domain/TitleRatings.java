package org.imdb.spark.domain;

public enum TitleRatings {
    TCONST("tconst"), AVERAGE_RATING("averageRating"), NUM_VOTES("numVotes");

    public final String value;

    TitleRatings(String value) {
        this.value = value;
    }
}
