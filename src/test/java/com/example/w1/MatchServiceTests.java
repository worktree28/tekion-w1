package com.example.w1;

import com.example.w1.models.Match;
import com.example.w1.repositories.es.MatchRepositoryES;
import com.example.w1.repositories.mongo.MatchRepositoryMongo;
import com.example.w1.services.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MatchServiceTests {
  @Autowired private MatchService matchService;
  @MockBean private MatchRepositoryES matchRepositoryES;
  @MockBean private MatchRepositoryMongo matchRepositoryMongo;
  @Autowired private Match match;

  @Test
  public void testShowAll() {
    when(matchRepositoryES.findAll(Pageable.unpaged())).thenReturn(new PageImpl<>(List.of(match)));
    when(matchRepositoryES.save(match)).thenReturn(match);
    when(matchRepositoryMongo.insert(match)).thenReturn(match);
    match = matchService.startMatch();
    List<Match> matches = matchService.showAll().getContent();
    verify(matchRepositoryES, times(1)).findAll(Pageable.unpaged());
    verify(matchRepositoryES, times(1)).save(match);
    verify(matchRepositoryMongo, times(1)).insert(match);
    verify(matchRepositoryMongo, never()).findAll(Pageable.unpaged());
    assertTrue(matches.contains(match));
  }

  @Test
  public void testViewById() {
    when(matchRepositoryES.save(match)).thenReturn(match);
    when(matchRepositoryMongo.insert(match)).thenReturn(match);
    match = matchService.startMatch();
    when(matchRepositoryES.findById(match.getId())).thenReturn(Optional.of(match));
    Match match1 = matchService.viewById(match.getId());
    assertEquals(match, match1);
  }

  @Test
  public void testViewByTeam() {
    when(matchRepositoryES.save(match)).thenReturn(match);
    when(matchRepositoryMongo.insert(match)).thenReturn(match);
    match = matchService.startMatch();
    when(matchRepositoryES.findByTeam1_NameOrTeam2_Name(
            match.getTeam1().getName(), match.getTeam1().getName(), Pageable.unpaged()))
        .thenReturn(new PageImpl<>(List.of(match)));
    when(matchRepositoryES.findByTeam1_NameOrTeam2_Name(
            match.getTeam2().getName(), match.getTeam2().getName(), Pageable.unpaged()))
        .thenReturn(new PageImpl<>(List.of(match)));
    List<Match> matches = matchService.viewByTeam(match.getTeam1().getName()).getContent();
    List<Match> matches1 = matchService.viewByTeam(match.getTeam2().getName()).getContent();
    assertTrue(matches.contains(match));
    assertTrue(matches1.contains(match));
  }

  // @Test
  // public void testDeleteAll() {
  //     matchService.deleteAll();
  //     assertEquals(0, matchService.showAll().getContent().size());
  // }
}
