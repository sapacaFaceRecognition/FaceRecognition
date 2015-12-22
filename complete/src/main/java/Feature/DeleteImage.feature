Feature: Delete Image

Background: User is logged in

Scenario: Successful delete of a saved image
	Given I am logged in as "admin"
	When I navigate to page "Browse Image"
	And I press the "Delete" Button
	Then The image will disappear
	
Scenario: Not successful delete of a saved image
	Given I am logged in as "user"
	When I navigate to page "Browse Image"
	And I press the "Delete" Button
	Then I see an error message "permission denied"
		
	