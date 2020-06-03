Feature: Get claims information
  Get the Status and other attributes from the existing claims

  Scenario: consumer makes call to GET /claims
    When the consumer calls /claims
    Then the consumer receives status code of 200
    And the consumer receives a list of claims with attributes "id, description"