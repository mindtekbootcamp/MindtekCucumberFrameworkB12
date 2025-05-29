@regression @smoke @smartbearApp
Feature: Smartbear Login Feature

  Background: Setup for login
    Given user navigates to smartbear app

  @loginPositive
  Scenario: Login Positive
    When user logs in with username "Tester" and password "test"
    Then user lands on main page

  @loginNegative
  Scenario: Login Negative
    When user logs in with username "Test" and password "tester"
    Then user gets error message "Invalid Login or Password."