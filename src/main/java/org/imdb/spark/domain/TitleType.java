package org.imdb.spark.domain;

public enum  TitleType {
    MOVIE("movie"), SHORT("short"), TVSERIES("tvseries"),
    TVEPISODE("tvepisode"), VIDEO("video");
    public final String value;

    TitleType(String value) {
        this.value = value;
    }
}
