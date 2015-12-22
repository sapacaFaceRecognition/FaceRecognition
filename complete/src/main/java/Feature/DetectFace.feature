Feature: Detect Face
As a logged in User
I want to detect faces from an image
So I can use the information later

Background: User is logged in

Scenario: Start Upload Image
	Given I am logged in
	When I navigate to page "Face Detection"
	And I press the "Upload Image" Button
	And I select an Image with size "3" MB
	And I press the "Submit" Button
	Then I see the uploaded Image with detected Faces
	And I see the number of detected faces
	