package org.imdb.spark.domain;

public enum TitlePrincipals {
    TCONST("tconst"), ORDERING("ordering"), NCONST("nconst"),
    CATEGORY("category"), JOB("job"), CHARACTERS("characters");

    public final String value;

    TitlePrincipals(String value) {
        this.value = value;
    }
}
