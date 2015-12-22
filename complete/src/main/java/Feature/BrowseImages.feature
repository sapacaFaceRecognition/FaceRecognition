Feature: Browse Image

Background: User is logged in

Scenario: Successful browsing saved images
	Given I am logged in
	When I navigate to page "Browse Image"
	And The database load is successful
	Then I see the saved images in the database

Scenario: Not Successful browsing saved images
	Given I am logged in
	When I navigate to page "Browse Image"
	And The database load is not successful
	Then I see an error message "Browse Image failed, DB load failed"