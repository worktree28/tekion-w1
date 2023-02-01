package com.example.w1.models;

import lombok.Data;

@Data
public class Player {
    private String name;
    private Role role;
    private int runs;
    private int wickets;
    private int balls;

    public Player(String name, Role role) {
        this.name = name;
        this.role = role;
    }

}
