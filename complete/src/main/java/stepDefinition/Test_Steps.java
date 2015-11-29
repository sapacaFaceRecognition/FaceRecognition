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

import java.util.List;

public class Test_Steps {
	public WebDriver driver = null;
	public WebElement element = null;
	
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
