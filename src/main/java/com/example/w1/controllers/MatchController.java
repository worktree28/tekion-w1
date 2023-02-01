package com.example.w1.controllers;

import com.example.w1.models.Match;
import com.example.w1.services.MatchService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class MatchController {
    @Autowired
    private MatchService matchService;

    @GetMapping("/all")
    public List<Match> showAll(){
        return matchService.showAll();
    }

    @GetMapping("/play-match")
    public Match startMatch(){
        return matchService.startMatch();
    }
    @GetMapping("/view/{id}")
    public Match viewById(@PathVariable("id")String id){
        return matchService.viewById(id);
    }

    @PostMapping(
            value = "/view-team",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Match> viewByTeam(@RequestBody @NotNull TeamName teamName){
        return matchService.viewByTeam(teamName.name());
    }

    @DeleteMapping("/del")
    public void deleteMatches(){
        matchService.deleteAll();
    }
    record TeamName(String name){}

}
