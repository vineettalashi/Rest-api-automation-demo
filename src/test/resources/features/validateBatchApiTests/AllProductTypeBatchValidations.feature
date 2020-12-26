Feature: Batch API Validations Feature
  As a user
  I want to perform Batch Api Validations
  So that I acheive a business goal

	@ValidateBatch
   Scenario: Verify the Validate Batch endpoint for Valid Data with request as String
    Given User has valid data to post for validateBatch api
    When User posts the json request for validate batch api as string
    Then Verify the batch response is valid
    
	@ValidateBatch
   Scenario: Verify the Validate Batch endpoint for Invalid Data with request as String
    Given User has invalid data to post for validateBatch api
    When User posts the json request for validate batch api as string
    Then Verify the batch response status is 'ERROR'
    
       @ValidateBatchObject
   Scenario: Verify the Validate Batch endpoint for Valid Data with request as Object
    Given User has valid data to post for validateBatch api
    When User posts the json request for validate batch api as object
    Then Verify the batch response is valid
    
   @ValidateBatchObject
   Scenario: Verify the Validate Batch endpoint for Invalid Data with request as Object
    Given User has invalid data to post for validateBatch api
    When User posts the json request for validate batch api as object
    Then Verify the batch response status is 'ERROR'