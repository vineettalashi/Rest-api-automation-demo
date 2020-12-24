Feature: Common Validations Feature
  As a user
  I want to perform Trade Validations
  So that I acheive a business goal

  Scenario: Verify the Validate endpoint for Valid Data
    Given User has valid data to post for happy flow
    When User posts the json request 
    Then Verify the response is valid
    
   Scenario: Verify error message when value date is before trade date
    Given User has Value Date older than Trade Date in input
    When User posts the json request
    Then Verify the response contains the 'cannot be null and it has to be after trade date' error message
    
   Scenario: Verify error message when when value date is on weekend
    Given User has the Value Date on weekend in input
    When User posts the json request
    Then Verify the response contains the 'cannot fall on Saturday/Sunday' error message
    
    Scenario: Verify error message when Invalid Customer is provided
    Given User has Invalid counterparty/customer in input
   	When User posts the json request
    Then Verify the response contains the 'is not supported. Supported counterparties: [[PLUTO2, PLUTO1]]' error message
    
    Scenario: Verify error message when Invalid Legal Entity is provided. Requirement: It should accept only CS Zurich as LE. Defect: It is accepting any string as Legal Entity
    Given User has Invalid Legal entity in input
   	When User posts the json request
    Then Verify the response contains the 'is not supported. Supported Legal entity: [CS Zurich]' error message