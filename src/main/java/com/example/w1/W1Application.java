package com.example.w1;

import com.example.w1.repositories.mongo.MatchRepositoryMongo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = MatchRepositoryMongo.class)
public class W1Application {
  public static void main(String[] args) {
    SpringApplication.run(W1Application.class, args);
  }
}
