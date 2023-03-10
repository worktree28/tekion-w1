package com.example.w1;

import java.util.ArrayList;

import com.example.w1.models.Player;
import com.example.w1.models.Role;
import com.example.w1.models.Team;
import com.example.w1.services.PlayInnings;
import com.example.w1.services.RunsPerBall;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MatchTests {
    @Test
    public void testRunPerBall(){
        Player player = new Player("xyz", Role.BATSMAN);
        for(int i=0; i<10; i++){
            player.setRole(Role.BATSMAN);
            int runs = RunsPerBall.getRuns(player);
            assertTrue(runs >= -1 && runs <= 6);
            player.setRole(Role.ALL_ROUNDER);
            runs = RunsPerBall.getRuns(player);
            assertTrue(runs >= -1 && runs <= 6);
            player.setRole(Role.BOWLER);
            runs = RunsPerBall.getRuns(player);
            assertTrue(runs >= -1 && runs <= 2);
            player.setRole(Role.WICKET_KEEPER);
            runs = RunsPerBall.getRuns(player);
            assertTrue(runs >= -1 && runs <= 6);
        }
    }
    @Test
    public void testPlayInnings(){
        Team team1 = new Team();
        Team team2 = new Team();
        PlayInnings playInnings = new PlayInnings(team1, team2, true);
        playInnings.start();
        ArrayList<ArrayList<Character>> scoreBoard = playInnings.getScoreBoard();
        assertTrue(scoreBoard.size() <= 20);
        for(ArrayList<Character> over : scoreBoard){
            assertTrue(over.size() <= 6);
            for(Character ball : over){
                assertTrue(ball == 'W' || (ball >= '0' && ball <= '6'));
            }
        }
    }
}