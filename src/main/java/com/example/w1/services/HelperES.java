package com.example.w1.services;

import com.example.w1.models.Match;
import com.example.w1.repositories.es.MatchRepositoryES;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HelperES {
  private final MatchRepositoryES matchRepositoryES;

  public void deleteAll() {
    matchRepositoryES.deleteAll();
  }

  public Page<Match> findByTeam(String team) {
    return matchRepositoryES.findByTeam1_NameOrTeam2_Name(team, team, Pageable.unpaged());
  }

  public Page<Match> findAll() {
    return matchRepositoryES.findAll(Pageable.unpaged());
  }

  public Match findById(String id) {
    return matchRepositoryES
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found"));
  }

  public void save(Match match) {
    matchRepositoryES.save(match);
  }

  public List<Match> partialSearch(String name) {
    return matchRepositoryES.findByPartialText(name, Pageable.unpaged()).getContent();
  }
}
