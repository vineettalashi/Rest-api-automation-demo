Feature: Invalid Product Type validations Feature
  As a user
  I want to perform Trade Validations
  So that I acheive a business goal
    
  Scenario: Verify error message when Invalid Product Type is provided.
    Given User has Invalid Product Type in input
   	When User posts the json request
    Then Verify the response contains the 'Could not resolve type id' error message