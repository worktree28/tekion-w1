package com.example.w1.controllers;

import com.example.w1.models.Match;
import com.example.w1.repositories.MatchRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
class ViewTeamMatches {
    @Autowired
    MatchRepository repository;
    record TeamName(String name){}
    @PostMapping(
            value = "/view-team",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Match> viewByTeam1OrTeam2(@RequestBody @NotNull TeamName teamName){
        List<Match> matches = repository.findByTeam1Name(teamName.name());
        matches.addAll(repository.findByTeam2Name(teamName.name()));
        if(matches.isEmpty())   throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        return matches;
    }
}