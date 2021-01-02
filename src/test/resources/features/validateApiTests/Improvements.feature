Feature: Forward validations Feature using data tables and examples
  As a user
  I want to perform Forward Validations
  So that I acheive a business goal

	@ExamplesTest @Improvement
   Scenario Outline: Verify the Validate endpoint for Valid Data
    Given User has valid data to post for '<Type>'
    When User posts the json request 
    Then Verify the response is valid
    
    Examples:
    |Type|
    |Forward|
    |Spot|
    |Trade|
    
	@DatatableTest @Improvement
   Scenario: Verify error message when Expiry Date is after Delivery Date
    Given User has data having Expiry Date after Delivery Date
    When User posts the json request 
    Then Verify the response contains the error messages
    |Expiry date|
    |has to be before delivery date|