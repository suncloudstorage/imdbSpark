package org.imdb.spark.service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.imdb.spark.domain.TitleBasics;
import org.imdb.spark.domain.TitleRatings;
import org.imdb.spark.domain.TitleType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.apache.spark.sql.functions.col;
import static org.imdb.spark.constant.ImdbConstants.SKIP_LOWER_LIMIT_VALUE;
import static org.imdb.spark.constant.ImdbConstants.SKIP_UPPER_LIMIT_VALUE;
import static org.imdb.spark.constant.ImdbSchemaConstants.IMDB_TITLE_BASICS_CSV_SCHEMA;
import static org.imdb.spark.constant.ImdbSchemaConstants.IMDB_TITLE_RATINGS_CSV_SCHEMA;
import static org.imdb.spark.constant.SparkConstants.*;

@Service
public class ImdbService {

    @Value("${hadoop.home.dir}")
    private String hadoopHomeDir;
    @Value("${spark.master}")
    private String sparkMaster;
    @Value("${spark.app.name}")
    private String sparkAppName;
    @Value("${spark.partitions}")
    private String sparkPartitions;
    @Value("${spark.sql.session.timeZone}")
    private String sparkSqlSessionTimeZone;
    @Value("${spark.csv.delimiter}")
    private String sparkCsvDelimiter;
    @Value("${imdb.title.basics.path}")
    private String imdbTitleBasicsPath;
    @Value("${imdb.title.ratings.path}")
    private String imdbTitleRatingsPath;

    public Dataset<Row> getPopularMovies(int minimumVotes, int countOfMovies, int fromYear, int toYear) {
        SparkSession sparkSession = getSparkSession();

        Dataset<Row> imdbTitleBasicsDataSet = getDataSetByCsvPath(imdbTitleBasicsPath, sparkSession, IMDB_TITLE_BASICS_CSV_SCHEMA);
        Dataset<Row> imdbTitleRatingsDataSet = getDataSetByCsvPath(imdbTitleRatingsPath, sparkSession, IMDB_TITLE_RATINGS_CSV_SCHEMA);

        Dataset<Row> moviesDataSet = imdbTitleBasicsDataSet
                .filter(col(TitleBasics.TITLE_TYPE.value).equalTo(TitleType.MOVIE.value));

        if (fromYear != SKIP_LOWER_LIMIT_VALUE) {
            moviesDataSet = moviesDataSet.filter(col(TitleBasics.START_YEAR.value).$greater$eq(fromYear));
        }

        if (toYear != SKIP_UPPER_LIMIT_VALUE) {
            moviesDataSet = moviesDataSet.filter(col(TitleBasics.START_YEAR.value).$less$eq(toYear));
        }

        Dataset<Row> titlesFilteredNumVotes = imdbTitleRatingsDataSet.filter(col(TitleRatings.NUM_VOTES.value).$greater(minimumVotes));
        Dataset<Row> moviesFilteredNumVotes = titlesFilteredNumVotes.join(moviesDataSet, TitleRatings.TCONST.value);
        Dataset<Row> moviesFilteredNumVotesAndAverageRatings = moviesFilteredNumVotes
                .orderBy(moviesFilteredNumVotes.col(TitleRatings.AVERAGE_RATING.value).desc())
                .limit(countOfMovies);

        return moviesFilteredNumVotesAndAverageRatings
                .select(TitleBasics.TCONST.value, TitleBasics.ORIGINAL_TITLE.value, TitleRatings.NUM_VOTES.value,
                        TitleRatings.AVERAGE_RATING.value, TitleBasics.START_YEAR.value);
    }

    private SparkSession getSparkSession() {
        System.setProperty(HADOOP_HOME_DIR, hadoopHomeDir);
        SparkSession sparkSession = SparkSession.builder()
                .master(sparkMaster)
                .appName(sparkAppName)
                .getOrCreate();

        sparkSession.conf().set(SPARK_SQL_SHUFFLE_PARTITIONS, sparkPartitions);
        sparkSession.conf().set(SPARK_SQL_SESSION_TIME_ZONE, sparkSqlSessionTimeZone);

        return sparkSession;
    }

    public Dataset<Row> getDataSetByCsvPath(final String path, SparkSession sparkSession, StructType structType) {

        return sparkSession.read()
                .option(DELIMITER_LABEL, sparkCsvDelimiter)
                .option(HEADER, true)
                .schema(structType)
                .csv(path);
    }
}
