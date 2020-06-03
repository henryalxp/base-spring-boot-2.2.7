Feature: Get policies information
  Get the Status and other attributes from the existing policies

  Scenario: consumer makes call to GET /policies
    When the consumer calls /policies
    Then the consumer receives status code of 200
    And the consumer receives a list of objects with attributes "id,description,status,policyNumber"