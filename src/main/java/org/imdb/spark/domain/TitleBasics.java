package org.imdb.spark.domain;

public enum TitleBasics {
    TCONST("tconst"), TITLE_TYPE("titleType"), PRIMARY_TITLE("primaryTitle"),
    ORIGINAL_TITLE("originalTitle"), IS_ADULT("isAdult"), START_YEAR("startYear"),
    END_YEAR("endYear"), RUNTIME_MINUTES("runtimeMinutes"), GENRES("genres");

    public final String value;

    TitleBasics(String value) {
        this.value = value;
    }
}
