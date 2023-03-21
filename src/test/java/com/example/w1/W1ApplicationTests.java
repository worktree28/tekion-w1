package com.example.w1;

import com.example.w1.controllers.MatchController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class W1ApplicationTests {

  @Autowired private MatchController matchController;
  @LocalServerPort private int port;
  @Autowired private TestRestTemplate restTemplate;

  @Test
  void contextLoads() {
    assertNotNull(matchController);
  }

  @Test
  public void testEndpoint() {
    ResponseEntity<String> response =
        restTemplate.getForEntity("http://localhost:" + port + "/play-match", String.class);
    assertEquals(HttpStatus.OK, response.getStatusCode());
  }
}
