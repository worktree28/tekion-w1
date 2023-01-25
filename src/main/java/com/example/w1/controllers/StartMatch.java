package com.example.w1.controllers;

import com.example.w1.models.Match;
import com.example.w1.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartMatch {
    @Autowired
    Match match;
    @Autowired
    MatchRepository matchRepository;
    @GetMapping("/play-match")
    public Match startMatch( ){
        match.start();
        matchRepository.insert(match);
        return match;
    }
}
