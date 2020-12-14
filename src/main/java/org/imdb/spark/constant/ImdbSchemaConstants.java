package org.imdb.spark.constant;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.imdb.spark.domain.TitleBasics;
import org.imdb.spark.domain.TitleRatings;

public class ImdbSchemaConstants {

    public static final StructType IMDB_TITLE_BASICS_CSV_SCHEMA = new StructType(new StructField[]{
            new StructField(TitleBasics.TCONST.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleBasics.TITLE_TYPE.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleBasics.PRIMARY_TITLE.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleBasics.ORIGINAL_TITLE.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleBasics.IS_ADULT.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleBasics.START_YEAR.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleBasics.END_YEAR.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleBasics.RUNTIME_MINUTES.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleBasics.GENRES.value, DataTypes.StringType, false, Metadata.empty())
    });

    public static final StructType IMDB_TITLE_RATINGS_CSV_SCHEMA = new StructType(new StructField[]{
            new StructField(TitleRatings.TCONST.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleRatings.AVERAGE_RATING.value, DataTypes.DoubleType, false, Metadata.empty()),
            new StructField(TitleRatings.NUM_VOTES.value, DataTypes.LongType, false, Metadata.empty())
    });

    public static final StructType MOVIES_CSV_SCHEMA = new StructType(new StructField[]{
            new StructField(TitleRatings.TCONST.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleBasics.ORIGINAL_TITLE.value, DataTypes.StringType, false, Metadata.empty()),
            new StructField(TitleRatings.NUM_VOTES.value, DataTypes.LongType, false, Metadata.empty()),
            new StructField(TitleRatings.AVERAGE_RATING.value, DataTypes.DoubleType, false, Metadata.empty()),
            new StructField(TitleBasics.START_YEAR.value, DataTypes.StringType, false, Metadata.empty())
    });
}
