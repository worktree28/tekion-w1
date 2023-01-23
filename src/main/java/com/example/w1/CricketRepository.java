package com.example.w1;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CricketRepository extends MongoRepository<Match,String > {
    List<Match> findByTeam1OrTeam2(Team t1, Team t2);
    @Query("{'team1:.name': ?0}")
    List<Match> findByName(final String name);
}
