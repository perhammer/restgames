package com.perhammer.joshua.stats;

import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping(value = "/stats", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Stats> getStats() {

        return statsService.getStats();
    }

}
