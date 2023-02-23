package com.example.w1;

import com.example.w1.models.Match;
import com.example.w1.services.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MatchServiceTests{
    @Autowired
    private MatchService matchService;
    @Test
    public void testShowAll(){
        Match match = matchService.startMatch();
        Match match1 = matchService.startMatch();
        List<Match> matches = matchService.showAll();
        assertTrue(matches.contains(match));
        assertTrue(matches.contains(match1));
    }
    @Test
    public void testStartMatch(){
        int size = matchService.showAll().size();
        matchService.startMatch();
        assertEquals(size+1, matchService.showAll().size());
    }
    @Test
    public void testViewById(){
        Match match = matchService.startMatch();
        Match match1 = matchService.viewById(match.getId());
        assertEquals(match, match1);
    }
    @Test
    public void testViewByTeam(){
        Match match = matchService.startMatch();
        List<Match> matches = matchService.viewByTeam(match.getTeam1().getName());
        List<Match> matches1 = matchService.viewByTeam(match.getTeam2().getName());
        assertTrue(matches.contains(match));
        assertTrue(matches1.contains(match));
    }
    @Test
    public void testDeleteAll(){
        testStartMatch();
        matchService.deleteAll();
        assertEquals(0, matchService.showAll().size());
    }
}