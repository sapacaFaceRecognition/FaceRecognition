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
	
	@Then("^I see the uploaded Image with detected Faces$")
	public void i_sse_uploaded_image() throws Throwable {
		selenium.checkUploadedImage();
	}
	
	
	@Then("^I see an error message$")
	public void i_see_an_error_message() throws Throwable {
	    selenium.errorMessage();
	    throw new PendingException();
	}
	
	
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
