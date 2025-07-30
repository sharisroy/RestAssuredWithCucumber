Feature: Add to Cart API

  @smoke
  Scenario: Add product to cart successfully
    Given I prepare the add to cart payload
    When I want to send a POST request to "user/add-to-cart"
    Then I should receive a 200 status code
    And I want to validate the add to cart response "Product Added To Cart"
