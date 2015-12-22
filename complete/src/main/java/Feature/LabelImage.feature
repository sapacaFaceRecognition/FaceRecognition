Feature: Label Image
As a logged in User
I want to label detected Faces with more information
So I can use the information later

Background: User is logged in

Scenario: Start Label
	Given I am logged in
	And I successfully uploaded an image and faces were detected
	When I see the uploaded Image with detected Faces
	And I press the "next" Button
	Then I can see "input form"
	
Scenario: Label success
	Given I am logged in
	And I see "labelImage" screen 
	When I input valid information
	Then I see a confirmation message "input valid"

Scenario: Label failed
	Given I am logged in
	And I see "labelImage" screen
	When I input not valid information
	Then I see an error message "input not valid"
	