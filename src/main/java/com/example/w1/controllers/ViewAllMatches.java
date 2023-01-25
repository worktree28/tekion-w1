package com.example.w1.controllers;

import com.example.w1.models.Match;
import com.example.w1.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ViewAllMatches {
    @Autowired
    MatchRepository matchRepository;
    @GetMapping("/all")
    public List<Match> showAll(){
        return matchRepository.findAll();
    }

}
