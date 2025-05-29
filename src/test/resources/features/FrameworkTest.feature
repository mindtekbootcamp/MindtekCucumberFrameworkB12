Feature: Testing Cucumber Framework

  @regression @smoke @frameworkTest
  Scenario: Navigating to Google
    Given user navigates to google
    Then user verifies main page with title

