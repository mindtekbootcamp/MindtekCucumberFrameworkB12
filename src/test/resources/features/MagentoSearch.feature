@magento @regression
Feature: Search Functionality

  Scenario: Searching for item and verifying search result
    Given user navigates to magento app
    When user searches for keyword "jacket"
    Then user verifies item names contain keywords
      | jacket     |
      | pullover   |
      | sweatshirt |
      | kit        |
      | jackshirt  |
      | shell      |

