package com.example.w1.services;

import com.example.w1.models.Match;
import com.example.w1.models.Team;
import com.example.w1.models.TeamStatus;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class PlayMatch {
  public Match start(Match match) {
    Faker faker = new Faker();
    match.setId(UUID.randomUUID().toString());
    match.setTeam1(new Team());
    match.setTeam2(new Team());
    match.setScoreBoard1(new ArrayList<ArrayList<Character>>());
    match.setScoreBoard2(new ArrayList<ArrayList<Character>>());
    if (faker.bool().bool()) {
      match.getTeam1().setStatus(TeamStatus.BATTING);
      match.getTeam2().setStatus(TeamStatus.BOWLING);
    } else {
      match.getTeam2().setStatus(TeamStatus.BATTING);
      match.getTeam1().setStatus(TeamStatus.BOWLING);
    }
    Team battingTeam =
        match.getTeam1().getStatus() == TeamStatus.BATTING ? match.getTeam1() : match.getTeam2();
    Team bowlingTeam =
        match.getTeam1().getStatus() == TeamStatus.BOWLING ? match.getTeam1() : match.getTeam2();

    PlayInnings playInnings1 = new PlayInnings(battingTeam, bowlingTeam, true);
    playInnings1.start();
    match.setTargetScore(battingTeam.getRuns() + 1);
    int target = battingTeam.getRuns() + 1;
    match.setScoreBoard1(playInnings1.getScoreBoard());
    battingTeam =
        match.getTeam1().getStatus() == TeamStatus.BOWLING ? match.getTeam1() : match.getTeam2();
    bowlingTeam =
        match.getTeam1().getStatus() == TeamStatus.BATTING ? match.getTeam1() : match.getTeam2();
    PlayInnings playInnings2 = new PlayInnings(battingTeam, bowlingTeam, false);
    playInnings2.setTargetScore(target);
    playInnings2.start();
    match.setScoreBoard2(playInnings2.getScoreBoard());
    if (battingTeam.getStatus() == TeamStatus.WON) {
      match.setResult(
          battingTeam.getName()
              + " won the match"
              + " by "
              + (10 - battingTeam.getWickets())
              + " wickets");
    } else if (battingTeam.getStatus() == TeamStatus.LOST) {
      match.setResult(
          bowlingTeam.getName()
              + " won the match"
              + " by "
              + (bowlingTeam.getRuns() - battingTeam.getRuns())
              + " runs");
    } else {
      match.setResult("Match Draw");
    }
    return match;
  }
  //
  //  private double ballsToOvers(int balls) {
  //    double overs = balls / 6;
  //    overs += (double) (balls % 6) / 10;
  //    return overs;
  //  }
}
