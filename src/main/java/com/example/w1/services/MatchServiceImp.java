package com.example.w1.services;

import com.example.w1.models.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MatchServiceImp implements MatchService {
  private final HelperES helperES;
  private final HelperMongo helperMongo;
  private final Match match;
  private final PlayMatch playMatch;

  public Page<Match> viewByTeam(String teamName) {
    CompletableFuture[] futures = new CompletableFuture<?>[] {
        CompletableFuture.supplyAsync(() -> helperES.findByTeam(teamName)),
        CompletableFuture.supplyAsync(() -> helperMongo.findByTeam(teamName))
    };

    CompletableFuture<Page<Match>> result = CompletableFuture.anyOf(futures)
        .thenApplyAsync(
            (resultObj) -> {
              // check cast is legal
              if (!(resultObj instanceof Page)) {
                throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error in db");
              }
              return (Page<Match>) resultObj;
            });
    return result.join();
  }

  public Match viewById(String id) {
    return helperES.findById(id);
  }

  public Page<Match> showAll() {
    return helperES.findAll();
  }

  public Match startMatch() {
    playMatch.start(match);
    insertMatch(match);
    return match;
  }

  public void insertMatch(Match match) {
    try {
      helperES.save(match);
      helperMongo.save(match);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Adding to db failed");
    }
  }

  public List<Match> partialSearch(String name) {
    return helperES.partialSearch(name);
  }

  public void deleteAll() {
    helperES.deleteAll();
    helperMongo.deleteAll();
  }

  // for validation
  public Page<Match> showAllES() {
    return helperES.findAll();
  }

  public Page<Match> showAllMongo() {
    return helperMongo.findAll();
  }
}
