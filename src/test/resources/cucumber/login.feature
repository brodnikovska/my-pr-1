Feature: Login

  Scenario: Login with valid credentials - smoke suite - Usage of String
    Given User opens 'chrome' and navigates to 'http://localhost:9090/'
    When User enters 'default' and '1q2w3e'
    Then User is logged in
    And Left side panel is visible