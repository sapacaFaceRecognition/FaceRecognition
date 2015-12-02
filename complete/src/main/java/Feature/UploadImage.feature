Feature: Upload Image

Background: User is logged in

Scenario: Successful upload of an Image and FaceDetection
	Given I am logged in
	When I navigate to page "Face Detection"
	And I press the "Upload Image" Button
	And I select an Image with size "3" MB
	And I press the "Submit" Button
	Then I see the uploaded Image with detected Faces

Scenario: Not Successful upload of an Image
	Given I am logged in
	When I navigate to page "Face Detection"
	And I press the "Upload Image" Button
	And I select an Image with size "6" MB
	And I press the "Submit" Button
	Then I see an error message
		
	
	