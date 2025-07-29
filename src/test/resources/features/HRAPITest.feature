@api @hrapp
Feature: Validating HR Api calls

  @regression @createDepartmentApi
  Scenario: Validating create department api call
    Given user creates department with post api call with data
      | Department name | Admission |
      | Location id     | 2400      |
    Then user validates created department is stored in database

  @regression @createEmployeeApi
  Scenario: Validating create employee api call
    Given user creates employee with post api call with data
      | First name | Mona       |
      | Last name  | Liza       |
      | Hire date  | 2025-05-05 |
    Then user validates employee created in database

  @regression @editDepartmentApi
  Scenario: Validating update department api call
    Given user creates department with post api call with data
      | Department name | Admission |
      | Location id     | 2400      |
    When user updates created department with put api call with data
      | Department name | Enrollment |
    Then user validates department is update in database

  @regression @deleteDepartmentApi
  Scenario: Validating delete department api call
    Given user creates department with post api call with data
      | Department name | Admission |
      | Location id     | 2400      |
    When user deletes created department with delete api call
    Then user validates department is deleted in database

  @regression @createDepartmentApiInvalidURL
  Scenario: Validating create department api call with invalid URL
    Given user creates department with post api call with invalid URL
    Then user validates "Invalid URL" error message

  @regression @createDepartmentApiNoHeader
  Scenario: Validating create department api call with no content type header
    Given user creates department with post api call with no content type header
    Then user validates 500 status code

  @regression @createDepartmentWithMissingField
  Scenario Outline: Validating create department api call with missing required fields in request body
    Given user creates department with post api call with missing required fields "<Missing field>" in request body
    Then user validates 400 status code
    And user validates "Department name and location_id are required" error
    Examples:
      | Missing field   |
      | department_name |
      | location_id     |


  @regression @createDepartmentWithInvalidFieldValues
  Scenario Outline: Validating create department api call with invalid fields in request body
    Given user creates department with post api call with invalid data
      | Department name | <Department name> |
      | Location id     | <Location id>     |
    Then user validates <Status code> status code
    And user validates "<Error message>" error
    Examples:
      | Department name                 | Location id | Error message                                      | Status code |
      | Enrollment                      | abc         | Location ID should be a number                     | 400         |
      | Enrollment                      | 123         | Internal Server Error while creating department    | 500         |
      | Enrollment                      | _empty      | Department name and location_id are required       | 400         |
      | Enrollment                      | !@#         | Location ID should be a number                     | 400         |
      | !@#$                            | 2400        | Department name can not be only special characters | 400         |
      | _empty                          | 2400        | Department name and location_id are required       | 400         |
      | uiniuvnunvusdjvdvdsddscsscwecab | 2400        | Internal Server Error while creating department    | 500         |
      | _space                          | 2400        | Internal Server Error while creating department    | 500         |
      | A                               | 2400        | Department name should have at least 3 characters  | 400         |


  @regression @deleteDepartmentNegativeTest
  Scenario: Validating delete department api call with non existing department id
    Given user finds non existing department id
    When user deletes non existing department id
    Then user validates 404 status code
    And user validates "Department not found" error

  @regression @getDepartmentsWithLimit
  Scenario Outline: Validating get departments limit query parameter
    Given user checks if departments exist in database otherwise creates departments
    When user gets departments with get api call with <limit> limit
    Then user validates <limit> departments in response
    Examples:
      | limit |
      | 5     |
      | 1     |
      | 10    |
      | 0     |

  @regression @getDepartmentWithOrder
  Scenario Outline: Validating get departments order query parameter
    Given user checks if departments exist in database otherwise creates departments
    When user gets departments with get api call with "<order>" order
    Then user validates departments are in "<order>" order in response
    Examples:
      | order |
      | asc   |
      | desc  |


  @regression @deleteDepartmentWithHruser
  Scenario Outline: Validating delete department with different roles
    Given user creates department with post api call with data
      | Department name | Admission |
      | Location id     | 2400      |
    When user deletes created department with delete api call with "<Role>"
    Then user validates <Status code> status code
    Examples:
      | Role       | Status code |
      | HR         | 403         |
      | HR_Manager | 204         |









