package com.example.w1.controllers;

import com.example.w1.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteAllMatches {
    @Autowired
    MatchRepository matchRepository;
    @GetMapping("/del")
    public String del(){
        try{
            matchRepository.deleteAll();
            return "Success";
        }
        catch (Exception e){
            return "Try again";
        }
    }
}
