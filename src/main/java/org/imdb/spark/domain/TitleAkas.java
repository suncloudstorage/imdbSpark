package org.imdb.spark.domain;

public enum TitleAkas {
    TITLE_AKAS("titleId"), ORDERING("ordering"), TITLE("title"),
    REGION("region"), LANGUAGE("language"), TYPES("types"),
    ATTRIBUTES("attributes"), IS_ORIGINAL_TITLE("isOriginalTitle");

    public final String value;

    TitleAkas(String value) {
        this.value = value;
    }
}
