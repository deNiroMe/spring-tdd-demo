Feature: Create a new book feature
  Scenario: create a book
    Given a book with following info
      | title   | summary | genre   |
      | summary | cucumber example  | test             |
    When I post for creating a new book
    Then a should get a response of status code 302
    And a new book is added