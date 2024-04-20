package com.codecool.healthriskanalytics.service;

import com.codecool.healthriskanalytics.model.Gender;
import com.codecool.healthriskanalytics.model.Person;

import java.util.Random;

public class PersonProvider {

    private static final Random random = new Random();
    private final Person[] persons;

    public PersonProvider(int count) {
        persons = generateRandomPersons(count);
    }

    public Person[] getPersons() {
        return persons;
    }

    private static Person[] generateRandomPersons(int count) {
        int id = 0;
        Person[] persons = new Person[count];

        for (int i = 0; i < count; i++) {
            persons[i] = new Person(id++, getRandomBirthDate(), getRandomGender(), getRandomHeight(), getRandomWeights());
        }

        return persons;
    }

    private static String getRandomBirthDate() {
        int year = random.nextInt(50) + 1950;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(25) + 1;

        return String.format("%d/%d/%d", day, month, year);
    }

    private static double getRandomHeight() {
        return random.nextDouble(1.5, 1.95);
    }

    private static int[] getRandomWeights() {
        int rangeStart = random.nextInt(70) + 40;
        int[] weights = new int[5];

        for (int i = 0; i < 5; i++) {
            weights[i] = random.nextInt(10) + rangeStart;
        }

        return weights;
    }

    private static Gender getRandomGender() {
        double chance = random.nextDouble(0, 1);
        return chance > 0.5 ? Gender.MALE : Gender.FEMALE;
    }


}
