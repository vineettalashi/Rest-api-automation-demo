Feature: Options validations Feature
  As a user
  I want to perform Trade Validations
  So that I acheive a business goal

	@OptionsValidData
   Scenario: Verify the Validate endpoint for Valid Data with EUROPEAN Style
    Given User has valid data to post for Options with European Style
    When User posts the json request 
    Then Verify the response is valid
    
    @OptionsValidData
   Scenario: Verify the Validate endpoint for Valid Data with AMERICAN Style
    Given User has valid data to post for Options with American Style
    When User posts the json request 
    Then Verify the response is valid
    
    @OptionsInValidData
    Scenario: Verify error message for Invalid Style
    Given User has Invalid Style in input
    When User posts the json request 
    Then Verify the response contains the 'Invalid option style | Valid option styles are: [AMERICAN, EUROPEAN]' error message
    
    @OptionsInValidData @Defect
   Scenario: Verify error message when Exercise Start Date is before Trade Date
    Given User has data having Exercise Start Date before Trade Date
    When User posts the json request 
    Then Verify the response status is 'ERROR'
    Then Verify the response contains the 'Trade date | has to be before exercise start date' error message
    
    @OptionsInValidData @Defect
   Scenario: Verify error message when Exercise Start Date is after Expiry Date
    Given User has data having Exercise Start Date after Expiry Date
    When User posts the json request 
    Then Verify the response status is 'ERROR'
    Then Verify the response contains the 'Exercise start date | has to be before expiry date' error message
    
    @OptionsInValidData
   Scenario: Verify error message when Expiry Date is after Delivery Date
    Given User has data having Expiry Date after Delivery Date
    When User posts the json request 
    Then Verify the response contains the 'Expiry date | has to be before delivery date' error message
    
    @OptionsInValidData
   Scenario: Verify error message when Premium Date is after Delivery Date
    Given User has data having Premium Date after Delivery Date
    When User posts the json request 
    Then Verify the response contains the 'Premium date | has to be before delivery date' error message
    
    @OptionsInValidData
    Scenario: Verify error message when Invalid Customer is provided
    Given User has Invalid counterparty for product type 'Options'
   	When User posts the json request
    Then Verify the response contains the 'is not supported. Supported counterparties: [[PLUTO2, PLUTO1]]' error message
    
    @OptionsInValidData @Defect
    Scenario: Verify error message when Invalid Legal Entity is provided. Requirement: It should accept only CS Zurich as LE. Defect: It is accepting any string as Legal Entity
    Given User has Invalid Legal entity for product type 'Options'
   	When User posts the json request
    Then Verify the response contains the 'is not supported. Supported Legal entity: [CS Zurich]' error message
    
    @OptionsInValidData
    Scenario: Verify error message when Invalid ISO Currency is provided in ccy pair
    Given User has Invalid ISO Currency for product type 'Options'
   	When User posts the json request
    Then Verify the response contains the 'Invalid currency pair' error message
    
    #Acc to requirement : American option style will have in addition the exerciseStartDate, which has to be after the trade date but before the expiry date
    #Defect : According to business requirement, exerciseStartDate should be present in request body but it is returning Success without it also.
    @OptionsInValidData @Defect1
    Scenario: Verify error message when American Style is provided without Exercise Start Date
    Given User has data with American Style and without Excercise Start Date
    When User posts the json request
    Then Verify the response status is 'ERROR'