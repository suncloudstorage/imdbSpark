package org.imdb.spark.domain;

public enum TitleCrew {
    TCONST("tconst"), DIRECTORS("directors"), WRITERS("writers");

    public final String value;

    TitleCrew(String value) {
        this.value = value;
    }
}
