package com.codecool.healthriskanalytics.ui;

import com.codecool.healthriskanalytics.model.Person;
import com.codecool.healthriskanalytics.service.AnalyticsService;

import java.util.Arrays;

public class HealthRiskUi {
    private final AnalyticsService analyticsService;
    private final Person[] persons;

    public HealthRiskUi(AnalyticsService analyticsService, Person[] persons) {
        this.analyticsService = analyticsService;
        this.persons = persons;
    }

    public void run() {
        System.out.println("Company's BMI statistics:");
        data();
        report();
    }

    public void data() {
        for (int i = 0; i < persons.length; i++) {
            System.out.println("Person #" + (i + 1) + ": ");
            System.out.println("Age: " + analyticsService.calculateAge(persons[i]));
            System.out.println("Gender: " + persons[i].gender());
            System.out.println("BMI Series: ");
            double[] bmiseries = analyticsService.calculateBmiSeries(persons[i]);
            for (double bmi :
                    bmiseries) {
                System.out.println((int) bmi);
            }
            System.out.println("Weight Condition: " + analyticsService.determineWeightCondition(persons[i]));
        }
    }

    public void report() {
        System.out.println("Percentage of potential overweight persons: " + analyticsService.calculateOrr(persons) * 100 + '%');
    }
}
