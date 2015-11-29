package cucumberTest;
 
import java.util.concurrent.TimeUnit;
 
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
 
public class SeleniumTest {
	private static WebDriver driver = null;
	private String browserName;
	private String browserVersion;
	private String mainUrl;
	
	public void setUp() {
		driver = new FirefoxDriver();
		mainUrl = "localhost:8080/login.html";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(mainUrl);
		driver.manage().window().maximize();
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		browserName = caps.getBrowserName();
		browserVersion = caps.getVersion();
		System.out.println("Running on " + browserName + " on version " + browserVersion);
	}
	
	public void login() {
		driver.findElement(By.id("inputUsername")).sendKeys("admin"); 	 
	    driver.findElement(By.id("inputPassword")).sendKeys("sapaca");
	    driver.findElement(By.id("login")).click();
	}
	
	public void clickButton(String button) {
		driver.findElement(By.id(button)).click();
	}
	
	public void goToHomePage() {
		driver.get(mainUrl);
	}
	
	public void navigateToPage(String page) {
		driver.findElement(By.id("menu-toggle")).click();
		driver.findElement(By.linkText(page)).click();
	}
}