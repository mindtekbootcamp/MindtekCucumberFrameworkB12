@regression @smartbear
Feature: Order Creation

  Background:
    Given user navigates to smartbear app
    When user logs in with username "Tester" and password "test"
    And user clicks on Order tab

  @calculator
  Scenario Outline: Validating order calculator
    And user selects product "<Product>" and quantity "<Quantity>"
    Then user validates total for product "<Product>" and quantity <Quantity>
    Examples:
      | Product     | Quantity |
      | MyMoney     | 9        |
      | FamilyAlbum | 10       |
      | ScreenSaver | 11       |

  @orderCreation
  Scenario: Validating order creation
    And user places an order
      | PRODUCT     | QUANTITY | CUSTOMER NAME | STREET     | CITY    | STATE | ZIP   | CARD | CARD NUM   | EXP DATE |
      | MyMoney     | 5        | John Doe      | 123 Dee RD | Boston  | MA    | 12345 | Visa | 1234567890 | 12/25    |
      | ScreenSaver | 10       | Adam Lee      | 123 Lee St | Chicago | IL    | 54321 | Amex | 9876543210 | 01/27    |
    Then user validates new order with success message "New order has been successfully added."
