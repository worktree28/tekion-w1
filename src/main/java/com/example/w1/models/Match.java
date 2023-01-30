package com.example.w1.models;

import java.util.ArrayList;

import com.example.w1.services.PlayInnings;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "matches")
@ToString
@Getter
@Setter
@Component
public class Match{
    @Id
    private ObjectId id;
    private Team team1;
    private Team team2;
    private ArrayList<ArrayList<Character>> scoreBoard1;
    private ArrayList<ArrayList<Character>> scoreBoard2;
    private int targetScore;
    String result;

}

