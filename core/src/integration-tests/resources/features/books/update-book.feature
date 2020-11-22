Feature: Update an existing book feature
  Scenario: update a book
    Given the book with values
      | id | 1  |
      | title  | updated title |
      | summary               |   updated example  |
      | genre                 | updated |
    When when i update the book with following values
    Then a should get a response of status code 302 for updating
    And an updated valid updated book