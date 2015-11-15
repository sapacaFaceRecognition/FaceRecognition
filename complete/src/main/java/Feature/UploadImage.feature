Feature Upload Image

Background: User is logged in

Scenario: Successful upload of an Image
	Given User is logged in
	And User is on Home Page
	When User Navigate to DetectFace Page
	And User Clicks on UploadImage Button 
	And Image is correct format
	Then Image displayed on screen
	
	