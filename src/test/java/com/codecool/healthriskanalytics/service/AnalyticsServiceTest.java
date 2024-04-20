package com.codecool.healthriskanalytics.service;

import com.codecool.healthriskanalytics.model.Gender;
import com.codecool.healthriskanalytics.model.Person;
import com.codecool.healthriskanalytics.model.WeightCondition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyticsServiceTest {
    private static final int CURRENT_YEAR = LocalDate.now().getYear();

    private final AnalyticsService analyticsService = new AnalyticsService();

    @Test
    void calculateAgeWhenCurrentTimeIsAfterBirthday() {

        // Arrange
        int birthYear = 1992;
        String birthDate = getBirthDateBeforeToday(birthYear);
        int expected = CURRENT_YEAR - birthYear;

        // Act
        int age = analyticsService.calculateAge(aPerson(birthDate));

        // Assert
        assertEquals(expected, age);
    }

    private String getBirthDateBeforeToday(int birthYear) {
        String birthDate;
        var beforeDate = LocalDate.now().minusDays(35);
        if (beforeDate.getYear() == LocalDate.now().getYear()) {
            birthDate = beforeDate.getDayOfMonth() + "/" + beforeDate.getMonthValue() + "/" + birthYear;
        } else {
            birthDate = "1/1/" + birthYear;
        }
        return birthDate;
    }

    @Test
    // If run on the last day of the year, we cannot ensure that birthday is after today.
    // and it would fail, giving a false error.
    @DisabledIf("isLastDayOfYear")
    void calculateAgeWhenCurrentTimeIsBeforeBirthday() {

        // Arrange
        int birthYear = 1992;
        String birthDate = getBirthDateAfterToday(birthYear);
        int expected = CURRENT_YEAR - birthYear - 1;

        // Act
        int age = analyticsService.calculateAge(aPerson(birthDate));

        // Assert
        assertEquals(expected, age);
    }

    private boolean isLastDayOfYear() {
        LocalDate currentDate = LocalDate.now();
        LocalDate disabledDate = LocalDate.of(currentDate.getYear(), 12, 31);

        return currentDate.equals(disabledDate);
    }

    private String getBirthDateAfterToday(int birthYear) {
        String birthDate;
        var beforeDate = LocalDate.now().plusDays(35);
        if (beforeDate.getYear() == LocalDate.now().getYear()) {
            birthDate = beforeDate.getDayOfMonth() + "/" + beforeDate.getMonthValue() + "/" + birthYear;
        } else {
            birthDate = "31/12/" + birthYear;
        }
        return birthDate;
    }

    @Test
    void calculateAgeWhenCurrentTimeIsOnBirthday() {

        // Arrange
        int birthYear = 1992;
        String birthDate = getBirthDateOnToday(birthYear);
        int expected = CURRENT_YEAR - birthYear;

        // Act
        int age = analyticsService.calculateAge(aPerson(birthDate));

        // Assert
        assertEquals(expected, age);
    }

    private String getBirthDateOnToday(int birthYear) {
        var today = LocalDate.now();
        return today.getDayOfMonth() + "/" + today.getMonthValue() + "/" + birthYear;
    }

    @Test
    void calculateBmiSeriesReturnsArrayWithCorrectSize() {
        Person person = healthyPerson();

        double[] bmiSeries = analyticsService.calculateBmiSeries(person);

        assertEquals(person.weights().length, bmiSeries.length);
    }

    @Test
    void calculateBmiSeriesReturnsCorrectValues() {
        int[] weights = {60, 61, 63, 65, 61};
        Person person = aPerson(weights);

        double[] expectedBmiSeries = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            expectedBmiSeries[i] = weights[i] / Math.pow(person.height(), 2);
        }

        double[] bmiSeries = analyticsService.calculateBmiSeries(person);

        Assertions.assertArrayEquals(expectedBmiSeries, bmiSeries);
    }

    @Test
    void determineWeightConditionReturnsOverWeight() {
        Person overWeightPerson = overweightPerson();

        WeightCondition weightCondition = analyticsService.determineWeightCondition(overWeightPerson);

        assertEquals(WeightCondition.Overweight, weightCondition);
    }

    @Test
    void determineWeightConditionReturnsHealthy() {
        Person healthyPerson = healthyPerson();

        WeightCondition weightCondition = analyticsService.determineWeightCondition(healthyPerson);

        assertEquals(WeightCondition.Healthy, weightCondition);
    }

    @Test
    void determineWeightConditionReturnsUnknown() {
        Person person = aPerson(new int[]{60});

        WeightCondition weightCondition = analyticsService.determineWeightCondition(person);

        assertEquals(WeightCondition.Unknown, weightCondition);
    }

    @Test
    void calculateOrrReturnsCorrectValue() {
        Person[] persons = {overweightPerson(), healthyPerson(), overweightPerson(), healthyPerson()};

        double orr = analyticsService.calculateOrr(persons);

        assertEquals(0.5, orr);
    }

    private Person overweightPerson() {
        return overweightPerson(Gender.MALE);
    }

    private Person overweightPerson(Gender gender) {
        return new Person(1, LocalDate.of(1992, Month.JULY, 21).toString(), gender, 1.7, new int[]{100, 103, 110, 109, 108});
    }

    private Person healthyPerson() {
        return healthyPerson(Gender.MALE);
    }

    private Person healthyPerson(Gender gender) {
        return new Person(1, "21/7/1992", gender, 1.7, new int[]{60, 61, 63, 65, 61});
    }

    private Person aPerson(String birthDate, Gender gender) {
        return new Person(1, birthDate, gender, 1.7, new int[]{60, 61, 63, 65, 61});
    }

    private Person aPerson(String birthDate) {
        return aPerson(birthDate, Gender.MALE);
    }

    private Person aPerson(int[] weights, Gender gender) {
        return new Person(1, "21/7/1992", gender, 1.7, weights);
    }

    private Person aPerson(int[] weights) {
        return aPerson(weights, Gender.MALE);
    }
}


