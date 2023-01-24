package com.example.w1;

import java.util.ArrayList;
import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

enum Role{
    BATSMAN, BOWLER, WICKET_KEEPER, ALL_ROUNDER
}
enum TeamStatus{
    BATTING, BOWLING, WON, LOST, DRAW
}
class RunsPerBall{
    public static int getRuns(Player player){
        Faker faker = new Faker();
        if(player.getRole()==Role.BATSMAN || player.getRole()==Role.WICKET_KEEPER){
            int result = faker.number().numberBetween(-1, 10);
            return result>6?faker.number().numberBetween(1,2):result;
        }
        else if(player.getRole()==Role.ALL_ROUNDER){
            return faker.number().numberBetween(-1, 7);
        }
        else{
            return faker.number().numberBetween(-1, 3);
        }
    }
}
@Getter
@Setter
@ToString
class Player{
    private String name;
    private Role role;
    private int runs=0;
    private int wickets=0;
    private int balls=0;
    public Player(String name, Role role){
        this.name = name;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
@ToString
@Getter
@Setter
class Team{
    private String name;
    private ArrayList<Player> players;
    private TeamStatus status;
    private int runs=0;
    private int wickets=0;
    private int balls=0;

    public TeamStatus getStatus() {
        return status;
    }

    public void setStatus(TeamStatus status) {
        this.status = status;
    }

    public Team() {
        // create random team name and 11 random players and add them to the team
        Faker faker = new Faker();
        this.name = faker.team().name();
        this.players = new ArrayList<Player>();
        for (int i = 0; i < 11; i++) {
            String nameTmp = faker.name().fullName();
            Role role;
            if (i == 0)
                role = Role.WICKET_KEEPER;
            else if (i < 4)
                role = Role.BATSMAN;
            else if (i < 6)
                role = Role.ALL_ROUNDER;
            else
                role = Role.BOWLER;
            Player player = new Player(nameTmp, role);
            this.players.add(player);
        }
    }

}
class Innings{
    private ArrayList<ArrayList<Character>> scoreBoard= new ArrayList<ArrayList<Character>>();
    private int overs=0;
    private Team battingTeam;
    private Team bowlingTeam;
    private boolean isFirstInnings;
    private int targetScore=0;
    public Innings(Team battingTeam, Team bowlingTeam, boolean isFirstInnings){
        this.isFirstInnings=isFirstInnings;
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
    }
    public ArrayList<ArrayList<Character>> getScoreBoard(){
        return this.scoreBoard;
    }
    public void setTargetScore(int targetScore){
        this.targetScore = targetScore;
    }
    public void startInnings(){
        // start the innings
        while(this.overs<20){// play the over
            for(int i=0; i<6; i++){
                // play the ball
                int runs = RunsPerBall.getRuns(this.battingTeam.getPlayers().get(this.battingTeam.getWickets()));
                if(runs!=-1){
                    this.battingTeam.getPlayers().get(this.battingTeam.getWickets()).setRuns(this.battingTeam.getPlayers().get(this.battingTeam.getWickets()).getRuns()+runs);
                    this.battingTeam.setRuns(this.battingTeam.getRuns()+runs);
                }
                this.battingTeam.getPlayers().get(this.battingTeam.getWickets()).setBalls(this.battingTeam.getPlayers().get(this.battingTeam.getWickets()).getBalls()+1);
                this.battingTeam.setBalls(this.battingTeam.getBalls()+1);
                if(targetScore!=0 && this.battingTeam.getRuns()>=targetScore){
                    this.battingTeam.setStatus(TeamStatus.WON);
                    this.bowlingTeam.setStatus(TeamStatus.LOST);
                    return;
                }
                if (this.scoreBoard.size() == this.overs) {
                    this.scoreBoard.add(new ArrayList<Character>());
                }
                if(runs==-1){
                    this.scoreBoard.get(this.overs).add('W');
                    this.battingTeam.setWickets(this.battingTeam.getWickets()+1);
                    if(this.battingTeam.getWickets()==10){
                        if(isFirstInnings){
                            return;
                        }
                        else if(this.battingTeam.getRuns()==this.targetScore){
                            this.battingTeam.setStatus(TeamStatus.DRAW);
                            this.bowlingTeam.setStatus(TeamStatus.DRAW);
                            return;
                        }
                        else{
                            this.battingTeam.setStatus(TeamStatus.LOST);
                            this.bowlingTeam.setStatus(TeamStatus.WON);
                            return;
                        }
                    }
                }
                else{
                    this.scoreBoard.get(this.overs).add(Character.forDigit(runs,10));
                }
                if(this.battingTeam.getStatus()==TeamStatus.LOST)
                    break;
            }
            this.overs++;
        }
        if(!isFirstInnings) {
            if (this.battingTeam.getRuns() == this.targetScore) {
                this.battingTeam.setStatus(TeamStatus.DRAW);
                this.bowlingTeam.setStatus(TeamStatus.DRAW);
            } else if (this.battingTeam.getRuns() > this.targetScore) {
                this.battingTeam.setStatus(TeamStatus.WON);
                this.bowlingTeam.setStatus(TeamStatus.LOST);
            } else {
                this.battingTeam.setStatus(TeamStatus.LOST);
                this.bowlingTeam.setStatus(TeamStatus.WON);
            }
        }
    }
}
@Document(collection = "matches")
@ToString
@Getter
@Setter
class Match{
    @Id
    private String id;
    private Team team1= new Team();
    private Team team2= new Team();
    private ArrayList<ArrayList<Character>> scoreBoard1;
    private ArrayList<ArrayList<Character>> scoreBoard2;
    private int targetScore;
    public Match(){
        Faker faker = new Faker();
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

        Innings innings1 = new Innings(battingTeam, bowlingTeam, true);
        innings1.startInnings();
        targetScore = battingTeam.getRuns()+1;
        int target = battingTeam.getRuns()+1;

        System.out.println(battingTeam.getName()+" is batting first");
        scoreBoard2 = innings1.getScoreBoard();
        System.out.println(innings1.getScoreBoard());
        System.out.println("Score = " + battingTeam.getRuns() +"/"+ battingTeam.getWickets()+" in "+ballsToOvers(battingTeam.getBalls()));
        System.out.println();

        System.out.println(bowlingTeam.getName()+" need "+target+ " to win");
        System.out.println();
        battingTeam = team1.getStatus()==TeamStatus.BOWLING?team1:team2;
        bowlingTeam = team1.getStatus()==TeamStatus.BATTING?team1:team2;
        System.out.println(battingTeam.getName()+ " is batting now");
        Innings innings2 = new Innings(battingTeam, bowlingTeam, false);
        innings2.setTargetScore(target);
        innings2.startInnings();
        scoreBoard1 = innings2.getScoreBoard();
        System.out.println(innings2.getScoreBoard());
        System.out.println("Final score = " + battingTeam.getRuns() +"/"+ battingTeam.getWickets()+" in "+ballsToOvers(battingTeam.getBalls()));
        System.out.println();

        if(battingTeam.getStatus()==TeamStatus.WON){
            System.out.println(battingTeam.getName()+" won the match"+ " by "+(10-battingTeam.getWickets())+" wickets");
        }
        else if(battingTeam.getStatus()==TeamStatus.LOST){
            System.out.println(bowlingTeam.getName()+" won the match"+ " by "+(bowlingTeam.getRuns()-battingTeam.getRuns())+" runs");
        }
        else{
            System.out.println("Match Draw");
        }
    }
    private double ballsToOvers(int balls){
        double overs= (int)balls/6;
        overs += (double)(balls%6)/10;
        return overs;
    }
}

public class Cricket{
    public static Match startMatch(){
        return new Match();
    }
}