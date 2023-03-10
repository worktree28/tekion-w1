package com.example.w1.services;

import com.example.w1.models.Match;
import com.example.w1.repositories.mongo.MatchRepositoryMongo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelperMongo {
  private final MatchRepositoryMongo matchRepositoryMongo;

  public Page<Match> findByTeam(String team) {
    return matchRepositoryMongo.findByTeam1NameOrTeam2Name(team, team, Pageable.ofSize(10));
  }

  public Page<Match> findAll() {
    return matchRepositoryMongo.findAll(Pageable.unpaged());
  }

  public void deleteAll() {
    matchRepositoryMongo.deleteAll();
  }

  public Match findById(String id) {
    return matchRepositoryMongo.findById(id).orElse(null);
  }

  public void save(Match match) {
    matchRepositoryMongo.insert(match);
  }
}