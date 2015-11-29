Feature: Upload Image

Background: User is logged in

Scenario: Successful upload of an Image
	Given I am logged in 
	When I navigate to "([^\"]*)"
	And I click on Upload Image Button
	And I select an Image
	Then Message displayed Uploaded Image successfully
	
	