Feature: Login

  Scenario Outline: Login with valid credentials on multiple browsers and environments
    Given User opens 'chrome' and navigates to 'http://localhost:9090/'
    When User passes <username> and <password>
    Then User is logged in
    And Left side panel is visible
    Examples:
      | username     | password   |
      | "default"      | "1q2w3e"     |
      | "superadmin"   | "erebus"     |


