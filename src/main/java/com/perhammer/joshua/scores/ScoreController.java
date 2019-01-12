package com.perhammer.joshua.scores;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping(value = "/scores/{teamname}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Score> getScores(@PathVariable("teamname") String teamname) {
        return Collections.emptyList();
    }


}
