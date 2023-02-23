package com.example.w1.services;

import com.example.w1.models.Team;
import com.example.w1.models.TeamStatus;

import java.util.ArrayList;

public class PlayInnings {
    private final ArrayList<ArrayList<Character>> scoreBoard = new ArrayList<>();
    private final Team battingTeam;
    private final Team bowlingTeam;
    private final boolean isFirstInnings;
    private int overs;
    private int targetScore;

    public PlayInnings(Team battingTeam, Team bowlingTeam, boolean isFirstInnings) {
        this.isFirstInnings = isFirstInnings;
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
    }

    public ArrayList<ArrayList<Character>> getScoreBoard() {
        return this.scoreBoard;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public void start() {
        // start the innings
        while (this.overs < 20) {// play the over
            for (int i = 0; i < 6; i++) {
                // play the ball
                int runs = RunsPerBall.getRuns(this.battingTeam.getPlayers().get(this.battingTeam.getWickets()));
                if (runs != -1) {
                    this.battingTeam.getPlayers().get(this.battingTeam.getWickets()).setRuns(this.battingTeam.getPlayers().get(this.battingTeam.getWickets()).getRuns() + runs);
                    this.battingTeam.setRuns(this.battingTeam.getRuns() + runs);
                }
                this.battingTeam.getPlayers().get(this.battingTeam.getWickets()).setBalls(this.battingTeam.getPlayers().get(this.battingTeam.getWickets()).getBalls() + 1);
                this.battingTeam.setBalls(this.battingTeam.getBalls() + 1);
                if (targetScore != 0 && this.battingTeam.getRuns() >= targetScore) {
                    this.battingTeam.setStatus(TeamStatus.WON);
                    this.bowlingTeam.setStatus(TeamStatus.LOST);
                    return;
                }
                if (this.scoreBoard.size() == this.overs) {
                    this.scoreBoard.add(new ArrayList<Character>());
                }
                if (runs == -1) {
                    this.scoreBoard.get(this.overs).add('W');
                    this.battingTeam.setWickets(this.battingTeam.getWickets() + 1);
                    if (this.battingTeam.getWickets() == 10) {
                        if (isFirstInnings) {
                            return;
                        } else if (this.battingTeam.getRuns() == this.targetScore) {
                            this.battingTeam.setStatus(TeamStatus.DRAW);
                            this.bowlingTeam.setStatus(TeamStatus.DRAW);
                            return;
                        } else {
                            this.battingTeam.setStatus(TeamStatus.LOST);
                            this.bowlingTeam.setStatus(TeamStatus.WON);
                            return;
                        }
                    }
                } else {
                    this.scoreBoard.get(this.overs).add(Character.forDigit(runs, 10));
                }
                if (this.battingTeam.getStatus() == TeamStatus.LOST)
                    break;
            }
            this.overs++;
        }
        if (!isFirstInnings) {
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
