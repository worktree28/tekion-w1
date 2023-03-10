package com.example.w1.controllers;

import com.example.w1.models.Match;
import com.example.w1.services.MatchService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {
  private final MatchService matchService;
  private final SimpMessagingTemplate template;
  @GetMapping("/all")
  public Page<Match> showAll() {
    return matchService.showAll();
  }

  @GetMapping("/play-match")
  public Match startMatch() {
    Match match = matchService.startMatch();
    template.convertAndSend("/topic/match", match);
    return match;
  }

  @SendTo("/topic/match")
  public Match broadcastMatch(@Payload Match match) {
    return match;
  }
  @GetMapping("/view/{id}")
  public Match viewById(@PathVariable("id") String id) {
    return matchService.viewById(id);
  }


  @PostMapping(
      value = "/view-team",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public Page<Match> viewByTeam(@RequestBody @NotNull TeamName teamName) {
    return matchService.viewByTeam(teamName.name());
  }

  @PostMapping(
      value = "/partial-search",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public List<Match> partialSearch(@RequestBody @NotNull TeamName teamName) {
    return matchService.partialSearch(teamName.name());
  }

  @DeleteMapping("/del")
  public void deleteMatches() {
    matchService.deleteAll();
  }
  // testing
  @GetMapping("/es-all")
  public Page<Match> showAllES() {
    return matchService.showAllES();
  }
  // testing
  @GetMapping("/mongo-all")
  public Page<Match> showAllMongo() {
    return matchService.showAllMongo();
  }

  record TeamName(String name) {}
}
