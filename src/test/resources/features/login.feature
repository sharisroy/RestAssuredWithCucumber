Feature: Login API Test

  @smoke
  Scenario: Login API Test with Valid Credentials
    Given I want send "<email>" and "<password>"
    When I want to send a POST request to "auth/login"
    Then I should receive a 200 status code
    And I want to validate the login response "Login Successfully"

  @negative @regression
  @invalid_login
  Scenario Outline: Login API Test with Invalid Credentials
    Given I want send "<email>" and "<password>"
    When I want to send a POST request to "auth/login"
    Then I should receive a 400 status code
    And I want to validate the login response "<errorMessage>"

    Examples:
      | email                 | password         | errorMessage                  |
      | wrong.email@gmail.com | wrongPassword123 | Incorrect email or password.  |
      | sq.haris@gmail.com    | badPassword      | Incorrect email or password.  |
