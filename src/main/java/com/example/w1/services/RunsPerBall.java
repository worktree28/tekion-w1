package com.example.w1.services;

import com.example.w1.models.Role;
import com.example.w1.models.Player;
import com.github.javafaker.Faker;

class RunsPerBall {
    public static int getRuns(Player player) {
        Faker faker = new Faker();
        if (player.getRole() == Role.BATSMAN || player.getRole() == Role.WICKET_KEEPER) {
            int result = faker.number().numberBetween(-1, 10);
            return result > 6 ? faker.number().numberBetween(1, 2) : result;
        } else if (player.getRole() == Role.ALL_ROUNDER) {
            return faker.number().numberBetween(-1, 7);
        } else {
            return faker.number().numberBetween(-1, 3);
        }
    }

}
