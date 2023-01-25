package com.example.w1.models;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
@Getter
@Setter
public class Team {
    private String name;
    private ArrayList<Player> players;
    private TeamStatus status;
    private int runs = 0;
    private int wickets = 0;
    private int balls = 0;

    public TeamStatus getStatus() {
        return status;
    }

    public void setStatus(TeamStatus status) {
        this.status = status;
    }

    public Team() {
        // create random team name and 11 random players and add them to the team
        Faker faker = new Faker();
        this.name = faker.team().name();
        this.players = new ArrayList<Player>();
        for (int i = 0; i < 11; i++) {
            String nameTmp = faker.name().fullName();
            Role role;
            if (i == 0)
                role = Role.WICKET_KEEPER;
            else if (i < 4)
                role = Role.BATSMAN;
            else if (i < 6)
                role = Role.ALL_ROUNDER;
            else
                role = Role.BOWLER;
            Player player = new Player(nameTmp, role);
            this.players.add(player);
        }
    }

}
