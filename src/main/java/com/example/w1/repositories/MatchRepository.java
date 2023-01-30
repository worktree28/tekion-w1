package com.example.w1.repositories;


import com.example.w1.models.Match;
import com.example.w1.models.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MatchRepository extends MongoRepository<Match,String > {
//    List<Match> findByTeam1OrTeam2(Team t1, Team t2);
    @Query("{'team1.name': ?0}")
    List<Match> findByTeam1Name(final String name);
    @Query("{'team2.name': ?0}")
    List<Match> findByTeam2Name(final String name);
}
