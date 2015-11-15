package cucumberTest;
 
import java.util.concurrent.TimeUnit;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
 
public class SeleniumTest {
		private static WebDriver driver = null;
	public static void main(String[] args) {
		// Create a new instance of the Firefox driver
		 
        driver = new FirefoxDriver();
 
        //Put a Implicit wait, this means that any search for elements on the page could take the time the implicit wait is set for before throwing exception
 
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
        //Launch the Online Store Website
 
        driver.get("http://www.store.demoqa.com");
        
        System.out.println("LogOut Successfully");
 
        // Close the driver
 
        driver.quit();
 
	}
 
}