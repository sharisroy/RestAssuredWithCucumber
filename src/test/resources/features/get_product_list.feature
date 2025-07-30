Feature: Get Product List API
  Scenario: Retrieve all product successfully
    Given I want to send valid information
    When I want to send a POST request to "product/get-all-products"
    Then I should receive a 200 status code
    And I want to validate the response "All Products fetched Successfully"

  Scenario: Retrieve products with blank token
    Given I want to send valid information
    And I set an blank token in the header
    When I want to send a POST request to "product/get-all-products"
    Then I should receive a 401 status code
    And I want to validate the response "Access denied. No token provided."

  Scenario: Retrieve products with invalid token
    Given I want to send valid information
    And I set an invalid token in the header
    When I want to send a POST request to "product/get-all-products"
    Then I should receive a 401 status code
    And I want to validate the response "Session Timeout"