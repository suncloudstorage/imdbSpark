package org.imdb.spark.controller;

import org.imdb.spark.service.ImdbService;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImdbController {

    private final ImdbService imdbService;

    public ImdbController(ImdbService imdbService) {
        this.imdbService = imdbService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<String>> getMostPopularMovies(@RequestParam("minimumVotes") int minimumVotes,
                                                             @RequestParam("countOfMovies") int countOfMovies,
                                                             @RequestParam("fromYear") int fromYear,
                                                             @RequestParam("toYear") int toYear) {
        Dataset<Row> popularMovies = imdbService.getPopularMovies(minimumVotes, countOfMovies, fromYear, toYear);
        return ResponseEntity.ok().body(popularMovies.toJSON().collectAsList());
    }
}
