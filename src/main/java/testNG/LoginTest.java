package testNG;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
	WebDriver driver;
	String browser;
	String url = "https://www.techfios.com/billing/?ng=admin/";
	
	// Element list
	/*By USERNAME = By.xpath("//*[@id=\"username\"]");
	By PASSWORD = By.xpath("//*[@id=\"password\"]");
	By LOGIN = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD = By.xpath("//h2[contains(text(), 'Dashboard')]");
	By CUSTOMER =By.xpath("//*[@id=\"side-menu\"]/li[3]/a");
	By ADDCUSTOMER= By.xpath("//*[@id=\"side-menu\"]/li[3]/a/i");
	By ADDCONTACT= By.xpath("//*[@id=\"phone\"]");
	By FULLNAME = By.xpath("//*[@id=\"account\"]");
	By COMPANY = By.xpath("//*[@id=\"cid\"]");
	By EMAIL = By.xpath("//*[@id=\"email\"]");
	By COUNTRY = By.xpath("//*[@id=\"select2-country-container\"]");*/
	
	By userNameField = By.xpath("//*[@id=\"username\"]");
	By passwordField = By.xpath("//*[@id=\"password\"]");
	By signInButtonField = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By dashboardHeaderField = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By customerMenuField = By.xpath("//*[@id=\"side-menu\"]/li[3]/a");
	By addCustomerMenuField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By addCustomerHeaderField = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By fullNameField = By.xpath("//*[@id=\"account\"]");
	By companyDropdownField = By.xpath("//*[@id=\"cid\"]");
	By emailField = By.xpath("//*[@id=\"email\"]");
	By countryDropdownField = By.xpath("//*[@id=\"select2-country-container\"]");
	
	
	/*String username = "demo@techfios.com";
	String password = "abc123";
	String expectedTitle = "Dashboard";*/
	
    @BeforeClass
	public void readConfig() {
		//InputStream //BufferedReader //FileReader //Scanner
		
		try {
			
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("User Browser" + browser);
			url = prop.getProperty("url");
			
		}catch(IOException e){
			
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {
		
		System.setProperty("webdriver.chrome.driver","driver\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		driver.get("https://www.techfios.com/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@Test(priority = 1)
	public void loginTest() {
		
		driver.findElement(userNameField).sendKeys("demo@techfios.com");
		driver.findElement(passwordField).sendKeys("abc123");
		driver.findElement(signInButtonField).click();

	//	Assert.assertEquals(driver.findElement(DASHBOARD).getText(), "Dashboard", "Page not found!!!");
		Assert.assertTrue(driver.findElement(dashboardHeaderField).isDisplayed(), "Page not Found");
	//	Assert.assertEquals(driver.getTitle(), "Dashboard- iBilling", "page not found");
	}
	
	@Test(priority = 2)
	public void addCustomer() throws InterruptedException {
		
		loginTest();
		driver.findElement(customerMenuField).click();
		driver.findElement(addCustomerMenuField).click();
		
		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(addCustomerHeaderField).getText(), "Add Contact", "Page not found!!!");
		int genNum = generateRandomNum(999);
		
		driver.findElement(fullNameField).sendKeys("Selenium" + genNum);
		
		selectFromDropdown(driver.findElement(companyDropdownField), "Techfios");
		
		driver.findElement(emailField).sendKeys(generateRandomNum(9999) + "demo@techfios.com");
		
		selectFromDropdown(driver.findElement(countryDropdownField), "Afghanistan");
	//	selectFromDropdown(countryDropdownField, "Afghanistan");
	//	By.xpath("//*[@id=\"select2-country-container\"]");
		
	}

	private int generateRandomNum(int boundaryNum) {
		
		Random rnd = new Random();
		int generatedNum = rnd.nextInt(boundaryNum);
		return generatedNum;
	}

/*	private void selectFromDropdown(By locator, String visibleText) {
		Select sel = new Select(driver.findElement(locator));
		sel.selectByVisibleText(visibleText);
		
	}*/

	private void selectFromDropdown(WebElement element, String visibleText) {
		
		Select sel = new Select(element);
		sel.selectByVisibleText(visibleText);
	}
	

}
