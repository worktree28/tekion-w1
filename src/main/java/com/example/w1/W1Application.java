package com.example.w1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableMongoRepositories(basePackageClasses = MatchRepository.class)
public class W1Application {
	@Autowired
	MatchRepository matchRepository;
	@Autowired
	Match match;
	public static void main(String[] args) {
		SpringApplication.run(W1Application.class, args);
	}

	@GetMapping("/")
	public String sm(){
		return "Hello world";
	}
	@GetMapping("/play-match")
	public Match startMatch( ){
		match.startMatch();
		matchRepository.insert(match);
		return match;
	}
	@GetMapping("/all")
	public List<Match> showAll(){
		return matchRepository.findAll();
	}
	@GetMapping("/del")
	public String del(){
		try{
			matchRepository.deleteAll();
			return "Success";
		}
		catch (Exception e){
			return "Try again";
		}
	}
}
