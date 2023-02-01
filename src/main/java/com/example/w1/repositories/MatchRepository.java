package com.example.w1.repositories;


import com.example.w1.models.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchRepository extends MongoRepository<Match,String > {
    List<Match> findByTeam1Name(final String name);
    List<Match> findByTeam2Name(final String name);
}
