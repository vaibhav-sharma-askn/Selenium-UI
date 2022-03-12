Feature: As a user
         I want to do login
        In order to use applications features

  @first
  Scenario: User logs in successfully with valid credentials
    Given the application is opened in browser
    When the user enters valid login credentials
    Then the user should redirect to dashboard

  @first
  Scenario: User logs in successfully with valid credentials
    Given the application is opened in browser
    When the user enters valid login credentials
    Then the user should redirect to dashboard
