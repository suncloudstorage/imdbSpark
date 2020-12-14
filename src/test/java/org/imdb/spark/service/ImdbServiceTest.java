package org.imdb.spark.service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.imdb.spark.constant.ImdbSchemaConstants.MOVIES_CSV_SCHEMA;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
class ImdbServiceTest {

    private static final String MOVIES_EXPECTED_RESULT_PATH = "src/test/resources/output/movies.test.result.tsv";

    @Autowired
    private ImdbService imdbService;

    @Value("${spark.master}")
    private String sparkMaster;
    @Value("${spark.app.name}")
    private String sparkAppName;

    private SparkSession sparkSession;

    @BeforeEach
    public void init() throws IllegalArgumentException {
        sparkSession = SparkSession.builder()
                .master(sparkMaster)
                .appName(sparkAppName)
                .getOrCreate();
    }

    @Test
    @DisplayName(value = "Test to get 10 most popular movies where minimum 100 votes and for the all time")
    public void shouldReturnMostPopularMovies() {
        Dataset<Row> popularMovies = imdbService.getPopularMovies(100, 10, 0, 9999);
        Dataset<Row> dataSetByCsvPath = imdbService.getDataSetByCsvPath(MOVIES_EXPECTED_RESULT_PATH, sparkSession, MOVIES_CSV_SCHEMA);

        List<String> resultMovies = popularMovies.toJSON().collectAsList();
        List<String> expectedMovies = dataSetByCsvPath.toJSON().collectAsList();

        assertEquals(expectedMovies, resultMovies);
    }

    @AfterEach
    public void afterClass() {
        if (sparkSession != null) {
            sparkSession.stop();
        }
    }

}