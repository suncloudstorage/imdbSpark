package org.imdb.spark.domain;

public enum TitleEpisode {
    TCONST("tconst"), PARENT_TCONST("parentTconst"), SEASON_NUMBER("seasonNumber"),
    EPISODE_NUMBER("episodeNumber");

    public final String value;

    TitleEpisode(String value) {
        this.value = value;
    }
}
