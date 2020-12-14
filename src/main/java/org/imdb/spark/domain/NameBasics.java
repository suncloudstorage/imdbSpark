package org.imdb.spark.domain;

public enum NameBasics {
    NCONST("nconst"), PRIMARY_NAME("primaryName"), BIRTH_YEAR("birthYear"),
    DEATH_YEAR("deathYear"), PRIMARY_PROFESSION("primaryProfession"), KNOWN_FOR_TITLES("knownForTitles");

    public final String value;

    NameBasics(String value) {
        this.value = value;
    }
}
