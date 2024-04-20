package com.codecool.healthriskanalytics.service;

import com.codecool.healthriskanalytics.model.Person;
import com.codecool.healthriskanalytics.model.WeightCondition;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

import static java.lang.Integer.parseInt;

public class AnalyticsService implements AnalyticsServiceInt {
    private static final double OVERWEIGHT_BMI_LIMIT = 25.0;
    private static final int OVERWEIGHT_YEARS_LIMIT = 3;

    @Override
    public int calculateAge(Person person) {
        LocalDate now = LocalDate.now();
        String[] birthInfosStr = person.birthDate().split("/");
        int[] birthInfosInt = new int[birthInfosStr.length];
        for (int i = 0; i < birthInfosStr.length; i++) {
            birthInfosInt[i] = Integer.parseInt(birthInfosStr[i]);
        }
        Period age = (Period.between(LocalDate.of(birthInfosInt[2], birthInfosInt[1], birthInfosInt[0]), now));
        return age.getYears();
    }

    @Override
    public double[] calculateBmiSeries(Person person) {
        double[] bmis = new double[person.weights().length];
        for (int i = 0; i < bmis.length; i++) {
            bmis[i] = calculateBmi(person.height(), person.weights()[i]);
        }
        return bmis;
    }

    @Override
    public double calculateBmi(double height, int weight) {
        return weight / Math.pow(height, 2);
    }

    @Override
    public WeightCondition determineWeightCondition(Person person) {
        if (person.weights().length < OVERWEIGHT_YEARS_LIMIT) {
            return WeightCondition.Unknown;
        } else if (calculateBmiSeries(person)[0] >= OVERWEIGHT_BMI_LIMIT &&
                calculateBmiSeries(person)[1] >= OVERWEIGHT_BMI_LIMIT &&
                calculateBmiSeries(person)[2] >= OVERWEIGHT_BMI_LIMIT) {
            return WeightCondition.Overweight;
        }
        return WeightCondition.Healthy;
    }

    @Override
    public double calculateOrr(Person[] persons) {
        double personsCounter = persons.length;
        double numberOfOverWeights = 0;
        for (int i = 0; i < personsCounter; i++) {
            if (determineWeightCondition(persons[i]) == WeightCondition.Overweight) {
                numberOfOverWeights++;
            }
        }
        return numberOfOverWeights / personsCounter;
    }
}
