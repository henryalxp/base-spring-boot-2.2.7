Feature: Get Zip code information

  Scenario Outline: consumer makes call to GET /zipcode
    When the consumer calls /zipcode and provides the code "<code>"
    Then the consumer receives status code of <status>
    And the consumer receives a response with post code "<post_code>" and country "<country>"

    Examples: 
      | code  | status | post_code | country |
      | 90210 |    200 |     90210 | USA     |
      | 85055 |    200 |     85055 | USA     |
