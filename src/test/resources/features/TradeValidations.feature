Feature: Trade Feature
  As a user
  I want to perform Trade Validations
  So that I acheive a business goal

  Scenario: Verify the Validate endpoint for Valid Data
    Given User has valid data to post for happy flow
    When User posts the json data
    Then Verify the response is valid
    
   Scenario: Verify error message when value date is before trade date
    Given User has Value Date older than Trade Date
    When User posts the json data
    Then Verify the response contains the required error message