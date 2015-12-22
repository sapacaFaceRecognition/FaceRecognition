package stepDefinition;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumberTest.SeleniumTest;

public class Test_Steps {
	// https://github.com/wayoflife/softwareengineering/blob/master/CucumberTests/src/cucumbertests/SeleniumTest.java
	// https://github.com/wayoflife/softwareengineering/blob/master/CucumberTests/src/cucumbertests/StepDefs.java
	
	public WebDriver driver = null;
	public WebElement element = null;
	private SeleniumTest selenium;
	
	public void setUp() {
		selenium = new SeleniumTest();
		selenium.setUp();
		selenium.goToHomePage();
	}
	
	@Given("^I am logged in$") 
	public void i_am_logged_in() throws Throwable {
		setUp();
		selenium.login();
	}
	
	@Given("^I am logged in as \"(.*?)\"$") 
	public void i_am_logged_in_as_admin(String user) throws Throwable {
		setUp();
		if (user.equals("user")) {
			selenium.loginUser();
		}
		else if (user.equals("admin")) {
			selenium.loginAdmin();
		}
	}
	
	@Given("^I successfully uploaded an image and faces were detected$")
	public void i_successfully_uploaded_an_image_and_faces_were_detected() throws Throwable {
		selenium.setUp();
		selenium.login();
		selenium.navigateToPage("Face Detection");
		selenium.clickButton("Upload Image");
		selenium.clickButton("Submit");
	}
	
	@Given("^I see \"([^\"]*)\" screen$")
	public void i_see_screen(String arg1) throws Throwable {
		selenium.seeScreen(arg1);
	}
	
	@When("^I press the \"(.*?)\" Button$") 
	public void i_press_the_button(String button) throws Throwable {
		selenium.clickButton(button);
	}
	
	@When("^I navigate to page \"(.*?)\"$")
	public void i_navigate_to_page(String page) throws Throwable {
		selenium.navigateToPage(page);
	}
	
	@When("^I select an Image with size \"([^\"]*)\" MB$")
	public void i_select_an_image(int size) throws Throwable {
		selenium.selectImage(size);
	}
	
	@When("^The database load is successful$")
	public void the_database_load_is_successful() throws Throwable {
		selenium.dbLoad();
	}
	
	@When("^The database load is not successful$")
	public void the_database_load_is_not_successful() throws Throwable {
		selenium.dbLoad();
	}
	
	@When("^I input valid information$")
	public void i_input_valid_information() throws Throwable {
		selenium.validInput();
	}
	
	@When("^I input not valid information$")
	public void i_input_not_valid_information() throws Throwable {
	   selenium.invalidInput();
	}
	
	@Then("^I see the uploaded Image$")
	public void i_sse_uploaded_image() throws Throwable {
		selenium.checkUploadedImage();
		
	}
	
	@Then("^I see the uploaded Image with detected Faces$")
	public void i_sse_uploaded_image_faces() throws Throwable {
		selenium.checkUploadedImage();	
	}
	
	@Then("^I see the number of detected faces$")
	public void i_see_the_number_of_detected_faces() throws Throwable {
		selenium.number();
	}
	
	@Then("^I see an error message \"([^\"]*)\"$")
	public void i_see_an_error_message(String msg) throws Throwable {
	    selenium.errorMessage(msg);
	}
	
	@Then("^I see the saved images in the database$")
	public void i_see_the_saved_images_in_the_database() throws Throwable {
		selenium.browse();
	}

	@Then("^The image will disappear$")
	public void the_image_will_disappear() throws Throwable {
		selenium.refreshPage();
	}
	
	@Then("^I can see \"([^\"]*)\"$")
	public void i_can_see(String arg1) throws Throwable {
		selenium.see(arg1);
	}
	
	@Then("^I see a confirmation message \"([^\"]*)\"$")
	public void i_see_a_conformation_message(String arg1) throws Throwable {
		selenium.confirmation();
	}
	
	// -------------------------------------------------------
	
	@Given("^User is logged in")
	public void user_is_logged_in() throws Throwable {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("localhost:8080/login.html");
		driver.findElement(By.id("inputUsername")).sendKeys("admin"); 	 
	    driver.findElement(By.id("inputPassword")).sendKeys("sapaca");
	    driver.findElement(By.id("login")).click();
	}
	
	@When("^User Navigate to DetectFace Page$")
	public void user_Navigate_to_DetectFace_Page() throws Throwable {
		driver.findElement(By.id("menu-toggle")).click();
		driver.findElement(By.linkText("Face Detection")).click();
	}

	@When("^User Clicks on UploadImage Button$")
	public void user_Clicks_on_UploadImage_Button() throws Throwable {
		//WebElement element = driver.findElement(By.xpath(".//*[@id='choose_file']/a")).click();
		//driver.findElement(By.xpath("//form/*[@name='uploadedFile']")).click();
		driver.findElement(By.xpath("//form/*[@name='uploadedFile']")).sendKeys("//users//caro//Desktop//unnamed.jpg");
	}
	
	@When("^User selects Image$")
	public void user_selects_Image() throws Throwable {
		driver.findElement(By.xpath("//form/*[@name='submit']")).click();
	}
	
	
	@Then("^Image displayed on screen$")
	public void image_displayed_on_screen() throws Throwable {
		
	}
	
	@Then("^Message displayed Uploaded Image successfully$")
	public void message_displayed_Uploaded_Image_successfully() throws Throwable {
		System.out.println("Image Upladed successfully!");
	}
}
