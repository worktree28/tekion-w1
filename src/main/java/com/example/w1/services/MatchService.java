package com.example.w1.services;

import com.example.w1.models.Match;
import com.example.w1.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private Match match;
    @Autowired
    private PlayMatch playMatch;

    public List<Match> viewByTeam(String teamName){
        List<Match> matches = matchRepository.findByTeam1Name(teamName);
        matches.addAll(matchRepository.findByTeam2Name(teamName));
        if(matches.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
        return matches;
    }

    public Match viewById(String id){
        if(matchRepository.existsById(id)){
            Optional<Match> match = matchRepository.findById(id);
            return match.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found");
    }

    public List<Match> showAll(){
        return matchRepository.findAll();
    }

    public Match startMatch(){
        playMatch.start(match);
        insertMatch(match);
        return match;
    }
    public void insertMatch(Match match){
        try{
            matchRepository.insert(match);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Adding to db failed");
        }
    }


    public void deleteAll(){
        matchRepository.deleteAll();
    }
}