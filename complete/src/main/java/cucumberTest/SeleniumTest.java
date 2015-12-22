package cucumberTest;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
 
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
 
public class SeleniumTest {
	private static WebDriver driver = null;
	WebElement element;
	private String browserName;
	private String browserVersion;
	private String mainUrl;
	private String uploadImage = "Upload Image";
	
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
	
	public void loginAdmin() {
		driver.findElement(By.id("inputUsername")).sendKeys("admin"); 	 
	    driver.findElement(By.id("inputPassword")).sendKeys("sapaca");
	    driver.findElement(By.id("login")).click();
	}
	
	public void loginUser() {
		driver.findElement(By.id("inputUsername")).sendKeys("user"); 	 
	    driver.findElement(By.id("inputPassword")).sendKeys("sapaca");
	    driver.findElement(By.id("login")).click();
	}
	
	public void clickButton(String button) {
		if (button.equals(uploadImage)) {
			element = driver.findElement(By.xpath("//form/*[@name='uploadedFile']"));
		} 
		else if (button.equals("Submit")) {
			driver.findElement(By.xpath("//form/*[@name='submit']")).click();
		}
		else if(button.equals("next")) {
			
		}
		else {	
			driver.findElement(By.id(button)).click();
		}
	}
	
	public void goToHomePage() {
		driver.get(mainUrl);
	}
	
	public void navigateToPage(String page) {
		driver.findElement(By.id("menu-toggle")).click();
		driver.findElement(By.linkText(page)).click();
	}
	
	public void selectImage(int size) {
		if (size <= 5) {
			element.sendKeys("//users//caro//Desktop//unnamed.jpg");
		} else if (size >= 5) {
			element.sendKeys("//users//caro//Desktop//65340036.JPG");
		}
	}
	
	public void checkUploadedImage() {
		//assertTrue(driver.findElement(By.id("detectingResult")) != null);
	}
	
	public void errorMessage(String msg) {
		System.out.println("Nope. Error :)");
		if (msg.equals("Upload not successful")) {
			assertTrue(driver.findElement(By.id("errorMsg")) != null);
		} 
		else if (msg.equals("Browse Image failed, DB load failed")) {
			assertTrue(driver.findElement(By.id("facesArray")) != null);
		}
		else if(msg.equals("permission denied")) {
			assertTrue(driver.findElement(By.id("permError")) != null);
		} else {
			
		}
	}
	
	public void dbLoad() throws  ClassNotFoundException, SQLException{
		 String dbUrl = "jdbc:mysql://localhost:8080/sapaca";                       
         String username = "root";   
         String password = "password";              
         String query = "select *  from sapaca;";        
         Class.forName("com.mysql.jdbc.Driver");          
         Connection con = DriverManager.getConnection(dbUrl,username,password);      
         Statement stmt = con.createStatement();                     
         ResultSet rs= stmt.executeQuery(query);                          
         while (rs.next()){
                     String id = rs.getString(1);                                        
         }           
         con.close();            
	}
	
	public void browse() {
		assertTrue(driver.findElement(By.id("saved01")) != null);
	}
	
	public void refreshPage() {
		int xpathcount = driver.findElements(By.xpath("/html/body/div[4]/div[2]/div/div[3]/ul/*")).size();
		driver.navigate().refresh();
		assertTrue(driver.findElements(By.xpath("/html/body/div[4]/div[2]/div/div[3]/ul/*")).size() == xpathcount - 1);
	}
	
	public void see(String arg1) {
		assertTrue(driver.findElement(By.id(arg1)) != null);
	}
	
	public void seeScreen(String arg1) {
		assertTrue(driver.findElement(By.id(arg1)) != null);
	
	}
	
	public void validInput() {
		driver.findElement(By.id("vorname")).sendKeys("testname");
		driver.findElement(By.id("nachname")).sendKeys("testname");
		driver.findElement(By.id("nationality")).sendKeys("test");
		driver.findElement(By.id("age")).sendKeys("12");
		driver.findElement(By.id("location")).sendKeys("testlocation");
	}
	
	public void invalidInput() {
		driver.findElement(By.id("vorname")).sendKeys("%testname");
		driver.findElement(By.id("nachname")).sendKeys("&testname");
		driver.findElement(By.id("nationality")).sendKeys("test");
		driver.findElement(By.id("age")).sendKeys("12");
		driver.findElement(By.id("location")).sendKeys("testlocation");
	}
	
	public void confirmation() {
		assertTrue(driver.findElement(By.id("confMsg")) != null);
	}
	
	public void number() {
		//assertTrue(driver.findElement(By.id("countFaces")) != null);
	}
}