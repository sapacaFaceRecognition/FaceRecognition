package stepDefinition;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Test_Steps {
	private static WebDriver driver = null;
	
	@Given("^User is logged in")
	public void user_is_logged_in() throws Throwable {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("localhost:8080/login.html");
		driver.findElement(By.id("inputUsername")).sendKeys("admin"); 	 
	    driver.findElement(By.id("inputPassword")).sendKeys("sapaca");
	    driver.findElement(By.id("login")).click();
		throw new PendingException();
	}
	
	@When("^User Nagivate to DetectFace Page$")
	public void user_Navigate_to_DetectFace_Page() throws Throwable {
		driver.findElement(By.xpath(".//*[@id='menu_toggle']/a")).click();
		driver.findElement(By.xpath(".//*[@id='detect_face']/a")).click();
	}
	
	@When("^User Clicks on ChooseFile Button$")
	public void user_Clicks_on_ChooseFile_Button() throws Throwable {
		driver.findElement(By.xpath(".//*[@id='choose_file']/a")).click();
	}
	
	@When("^User selects Image$")
	public void user_selects_Image() throws Throwable {
		// SELECT IMAGE
	}
	
	@When("^Image is correct format$")
	public void image_is_correct_format() throws Throwable {
		
	}
	
	@Then("^Image displayed on screen$")
	public void image_displayed_on_screen() throws Throwable {
		
	}
	
	@Then("^Message displayed Uploaded Image successfully$")
	public void message_displayed_Uploaded_Image_successfully() throws Throwable {
		System.out.println("Image Upladed successfully!");
	}
}
