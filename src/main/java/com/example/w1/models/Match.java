package com.example.w1.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Document(collection = "matches")
@Data
@Component
public class Match{
    String result;
    @Id
    private String id;
    private Team team1;
    private Team team2;
    private ArrayList<ArrayList<Character>> scoreBoard1;
    private ArrayList<ArrayList<Character>> scoreBoard2;
    private int targetScore;
}

