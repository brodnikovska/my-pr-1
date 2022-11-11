Feature: Left side bar

  Scenario: Scenario Number One - Usage of List<String>
    Given User opens 'edge' and navigates to 'http://localhost:9090/'
    And User enters 'default' and '1q2w3e'
    When User is logged in
    Then Following icons are present in left side bar
      | Dashboards       |
      | Launches         |
      | Filters          |
      | Debug            |
      | Project members  |
      | Project settings |