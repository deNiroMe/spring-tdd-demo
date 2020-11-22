Feature: Go to the index page feature
  Scenario: go to the index page
    Given i want to go to the index page
    When I call the root url "/" or the "/index" url
    Then a should get a response of status code 200 for the index url
    And get redirected to the "index/index" page