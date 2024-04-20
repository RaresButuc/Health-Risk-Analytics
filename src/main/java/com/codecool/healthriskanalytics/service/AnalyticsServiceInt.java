package com.codecool.healthriskanalytics.service;

import com.codecool.healthriskanalytics.model.Person;
import com.codecool.healthriskanalytics.model.WeightCondition;

public interface AnalyticsServiceInt {
    int calculateAge(Person person);

    double[] calculateBmiSeries(Person person);

    double calculateBmi(double height, int weight);

    WeightCondition determineWeightCondition(Person person);

    double calculateOrr(Person[] persons);
}
