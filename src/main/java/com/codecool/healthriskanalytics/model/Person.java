package com.codecool.healthriskanalytics.model;

import com.codecool.healthriskanalytics.model.Gender;

import java.util.Arrays;

public record Person(int id, String birthDate, Gender gender, double height, int[] weights) {
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", birthDate='" + birthDate + '\'' +
                ", gender=" + gender +
                ", height=" + height +
                ", weights=" + Arrays.toString(weights) +
                '}';
    }
}
