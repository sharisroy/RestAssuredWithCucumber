
Feature: Get Cart Product List API
  @smoke
  Scenario: Retrieve all Cart product successfully
    When I want to send a GET request to "order/get-orders-for-customer/"
    Then I should receive a 200 status code
    And I want to validate the get product response "Orders fetched for customer Successfully"