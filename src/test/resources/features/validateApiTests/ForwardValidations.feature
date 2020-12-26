Feature: Forward validations Feature
  As a user
  I want to perform Forward Validations
  So that I acheive a business goal

   Scenario: Verify the Validate endpoint for Valid Data
    Given User has valid data to post for 'Forward'
    When User posts the json request 
    Then Verify the response is valid
    
    @ForwardInvalidData
   Scenario: Verify error message when value date is before trade date
    Given User has Value Date older than Trade Date for product type 'Forward'
    When User posts the json request as object
    Then Verify the response contains correct error message when value date is before trade date
    Then Verify the response contains the 'cannot be null and it has to be after trade date' error message
    
    @ForwardInvalidData
    Scenario: Verify error message when Invalid Customer is provided
    Given User has Invalid counterparty for product type 'Forward'
   	When User posts the json request as object
   	Then Verify the response contains correct error message when Invalid Customer is provided
    Then Verify the response contains the 'is not supported. Supported counterparties: [[PLUTO2, PLUTO1]]' error message
    
   Scenario: Verify error message when when value date is on weekend
    Given User has the Value Date on weekend for product type 'Forward'
    When User posts the json request
    Then Verify the response contains the 'cannot fall on Saturday/Sunday' error message
    
    @Defect
    Scenario: Defect: Verify error message when Invalid Legal Entity is provided. Requirement: It should accept only CS Zurich as LE. Defect: It is accepting any string as Legal Entity
    Given User has Invalid Legal entity for product type 'Forward'
   	When User posts the json request
    Then Verify the response contains the 'is not supported. Supported Legal entity: [CS Zurich]' error message
    
    Scenario: Verify error message when Invalid ISO Currency is provided in ccy pair
    Given User has Invalid ISO Currency for product type 'Forward'
   	When User posts the json request
    Then Verify the response contains the 'Invalid currency pair' error message
    
    @ForwardInValidData @Defect
    Scenario: Defect: Verify error message when Amount is provided as 0,Trader is null, Rate is 0.
    Given User has amount1 and amount2 as zero , Rate is zero and Trader is empty in input data for product type 'Forward'
   	When User posts the json request as object
    Then Verify the response status is 'ERROR'