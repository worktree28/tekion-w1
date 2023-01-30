package com.example.w1.controllers;

import com.example.w1.models.Match;
import com.example.w1.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class ViewMatch {
    @Autowired
    MatchRepository repository;
    @GetMapping("/view/{id}")
    public Match viewById(@PathVariable("id")String id){
        if(repository.existsById(id)){
            Optional<Match> match = repository.findById(id);
            return match.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
    }
}
