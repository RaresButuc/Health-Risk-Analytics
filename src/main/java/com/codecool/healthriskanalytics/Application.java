package com.codecool.healthriskanalytics;

import com.codecool.healthriskanalytics.model.Gender;
import com.codecool.healthriskanalytics.model.Person;
import com.codecool.healthriskanalytics.service.AnalyticsService;
import com.codecool.healthriskanalytics.service.PersonProvider;
import com.codecool.healthriskanalytics.ui.HealthRiskUi;

public class Application {

    public static void main(String[] args) {
        PersonProvider personProvider = new PersonProvider(20);
        Person[] persons = personProvider.getPersons();
        AnalyticsService serviceMethods = new AnalyticsService();
        var ui = new HealthRiskUi(serviceMethods, persons);
        ui.run();
    }
}
