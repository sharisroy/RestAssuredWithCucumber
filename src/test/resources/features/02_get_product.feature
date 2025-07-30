Feature: Get Product List API
  @smoke
  Scenario: Retrieve all product successfully
    When I want to send a POST request to "product/get-all-products"
    Then I should receive a 200 status code
    And I want to validate the get product response "All Products fetched Successfully"

  @negative @regression
  Scenario: Retrieve products with blank token
    And I set an blank token in the header
    When I want to send a POST request to "product/get-all-products"
    Then I should receive a 401 status code
    And I want to validate the get product response "Access denied. No token provided."

  @negative @regression
  Scenario: Retrieve products with invalid token
    And I set an invalid token in the header
    When I want to send a POST request to "product/get-all-products"
    Then I should receive a 401 status code
    And I want to validate the get product response "Session Timeout"