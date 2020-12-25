Feature: Trade Validations Feature
  As a user
  I want to perform Trade Validations
  So that I acheive a business goal

  #Exception is coming for Product Type Trade after hitting the post request for all Valid Data
  Scenario: Verify the Validate endpoint for Valid Data. Exception is coming for Product Type Trade after hitting the post request for all Valid Data
    Given User has valid data to post for 'Trade'
    When User posts the json request 
    Then Verify the response is valid