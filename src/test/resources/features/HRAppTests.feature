Feature: Validating UI with DB

  @hrapp @regression
  Scenario: Validating created employee in UI is stored in Database
    Given user navigates to HRApp
    When user creates employee with data
      | Full Name   | Email                 | Job ID | Department     | Hire Date  | Salary |
      | Patel Harsh | patel.harsh@gmail.com | 5      | Administration | 05/01/2025 | 10000  |
    And user connects to database
    Then user validates that created employee is stored in database

  @hrapp @regression @deleteEmployee
  Scenario: Validated deleted employee in UI is removed from Database
    Given user navigates to HRApp
    When user creates employee with data
      | Full Name   | Email                 | Job ID | Department     | Hire Date  | Salary |
      | Patel Harsh | patel.harsh@gmail.com | 5      | Administration | 05/01/2025 | 10000  |
    And user connects to database
    Then user validates that created employee is stored in database
    When user deletes created employee in UI
    Then user validates that deleted employee is removed from database

    @hrapp @regression @editEmployee
  Scenario: Validate updated employee in UI is updated in Database
    Given user navigates to HRApp
    When user creates employee with data
      | Full Name   | Email                 | Job ID | Department     | Hire Date  | Salary |
      | Patel Harsh | patel.harsh@gmail.com | 5      | Administration | 05/01/2025 | 10000  |
    And user connects to database
    Then user validates that created employee is stored in database
    When user updated employee name to "John Doe"
    Then user validates employee name updated in Database

