package com.example.w1;

import java.util.ArrayList;
import com.github.javafaker.Faker;

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
    public String getName(){
        return this.name;
    }
    public Role getRole(){
        return this.role;
    }
    public int getRuns(){
        return this.runs;
    }
    public int getWickets(){
        return this.wickets;
    }
    public int getBalls(){
        return this.balls;
    }
    public void addRuns(int runs){
        this.runs = runs;
    }

}
class Team{
    private String name;
    private ArrayList<Player> players;
    private TeamStatus status;
    private int runs=0;
    private int wickets=0;
    private int balls=0;
    public Team(TeamStatus status){
        // create random team name and 11 random players and add them to the team
        Faker faker = new Faker();
        this.status = status;
        this.name = faker.team().name();
        this.players = new ArrayList<Player>();
        for(int i=0; i<11; i++){
            String nameTmp = faker.name().fullName();
            Role role;
            if(i==0)
                role = Role.WICKET_KEEPER;
            else if(i<4)
                role = Role.BATSMAN;
            else if(i<6)
                role = Role.ALL_ROUNDER;
            else
                role = Role.BOWLER;
            Player player = new Player(nameTmp, role);
            this.players.add(player);
        }
    }
    public String getName(){
        return this.name;
    }
    public ArrayList<Player> getPlayers(){
        return this.players;
    }
    public TeamStatus getStatus(){
        return this.status;
    }
    public int getRuns(){
        return this.runs;
    }
    public int getWickets(){
        return this.wickets;
    }
    public int getBalls(){
        return this.balls;
    }
    public void setRuns(int runs){
        this.runs = runs;
    }
    public void setWickets(int wickets){
        this.wickets = wickets;
    }
    public void setBalls(int balls){
        this.balls = balls;
    }
    public void setStatus(TeamStatus status){
        this.status = status;
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
                if(runs!=-1)
                    this.battingTeam.setRuns(this.battingTeam.getRuns()+runs);
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
class Match{
    public Match(){
        Faker faker = new Faker();
        Team team1;
        Team team2;
        if(faker.bool().bool()){
            // System.out.println("Team 1 is batting first");
            team1 = new Team(TeamStatus.BATTING);
            team2 = new Team(TeamStatus.BOWLING);
        }
        else{
            // System.out.println("Team 2 is batting first");
            team1 = new Team(TeamStatus.BOWLING);
            team2 = new Team(TeamStatus.BATTING);
        }
        Team battingTeam = team1.getStatus()==TeamStatus.BATTING?team1:team2;
        Team bowlingTeam = team1.getStatus()==TeamStatus.BOWLING?team1:team2;

        Innings innings1 = new Innings(battingTeam, bowlingTeam, true);
        innings1.startInnings();
        int target = battingTeam.getRuns()+1;
        System.out.println(battingTeam.getName()+" is batting first");
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
    public static void main(String[] args){
        startMatch();
    }
    public static void startMatch(){
        Match match = new Match();
    }
}