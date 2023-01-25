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
    public Match(){}
    public Match start(){
        Faker faker = new Faker();
        id = new ObjectId();
        team2 = new Team();
        team1 = new Team();
        scoreBoard1= new ArrayList<ArrayList<Character>>();
        scoreBoard2= new ArrayList<ArrayList<Character>>();

        if(faker.bool().bool()){
            // System.out.println("Team 1 is batting first");
            team1.setStatus(TeamStatus.BATTING);
            team2.setStatus(TeamStatus.BOWLING);
        }
        else{
            // System.out.println("Team 2 is batting first");
            team2.setStatus(TeamStatus.BATTING);
            team1.setStatus(TeamStatus.BOWLING);
        }
        Team battingTeam = team1.getStatus()==TeamStatus.BATTING?team1:team2;
        Team bowlingTeam = team1.getStatus()==TeamStatus.BOWLING?team1:team2;

        PlayInnings playInnings1 = new PlayInnings(battingTeam, bowlingTeam, true);
        playInnings1.start();
        targetScore = battingTeam.getRuns()+1;
        int target = battingTeam.getRuns()+1;

//        System.out.println(battingTeam.getName()+" is batting first");
        scoreBoard2 = playInnings1.getScoreBoard();
//        System.out.println(playInnings1.getScoreBoard());
//        System.out.println("Score = " + battingTeam.getRuns() +"/"+ battingTeam.getWickets()+" in "+ballsToOvers(battingTeam.getBalls()));
//        System.out.println();

//        System.out.println(bowlingTeam.getName()+" need "+target+ " to win");
//        System.out.println();
        battingTeam = team1.getStatus()==TeamStatus.BOWLING?team1:team2;
        bowlingTeam = team1.getStatus()==TeamStatus.BATTING?team1:team2;
//        System.out.println(battingTeam.getName()+ " is batting now");
        PlayInnings playInnings2 = new PlayInnings(battingTeam, bowlingTeam, false);
        playInnings2.setTargetScore(target);
        playInnings2.start();
        scoreBoard1 = playInnings2.getScoreBoard();
//        System.out.println(playInnings2.getScoreBoard());
//        System.out.println("Final score = " + battingTeam.getRuns() +"/"+ battingTeam.getWickets()+" in "+ballsToOvers(battingTeam.getBalls()));
//        System.out.println();

        if(battingTeam.getStatus()==TeamStatus.WON){
            result = battingTeam.getName()+" won the match"+ " by "+(10-battingTeam.getWickets())+" wickets";
        }
        else if(battingTeam.getStatus()==TeamStatus.LOST){
            result = bowlingTeam.getName()+" won the match"+ " by "+(bowlingTeam.getRuns()-battingTeam.getRuns())+" runs";
        }
        else{
            result = "Match Draw";
        }
//        System.out.println(result);
        return this;
    }
    private double ballsToOvers(int balls){
        double overs= (int)balls/6;
        overs += (double)(balls%6)/10;
        return overs;
    }
}

