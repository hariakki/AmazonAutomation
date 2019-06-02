package com.amazon.testBase;





import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import java.util.HashMap;

import java.util.Map;

import java.util.Properties;


// import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.amazon.utility.ExcelReader;
import com.amazon.utility.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;



public class TestBase {
	
	public static final Logger logger = Logger.getLogger(TestBase.class.getName());
	public static WebDriver driver;
	public static Properties OR;
	public File f1;
	public FileInputStream file;
	public ExcelReader excelreader;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	
	
	@BeforeSuite
	public void launchBrowser() throws IOException{
		
			loadPropertiesFile();
		
		Config config = new Config(OR);
		getBrowser(config.getBrowser());
		driver.get(config.getWebsite());
		logger.info("Lauched URL is --- " + config.getWebsite());
		driver.manage().window().maximize();
		
		
	}
	
	//3.0.1
	//FF:-47.0.2
	//0.15
	public void getBrowser(String browser){
		if(System.getProperty("os.name").contains("Window")){
			if(browser.equalsIgnoreCase("firefox")){
				//https://github.com/mozilla/geckodriver/releases
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/test/resources/executables/geckodriver.exe");
				driver = new FirefoxDriver();
				
			}
			else if(browser.equalsIgnoreCase("chrome")){
				//https://chromedriver.storage.googleapis.com/index.html
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/executables/chromedriver.exe");
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_setting_values.notifications", 2);
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-infobars");
				driver = new ChromeDriver(options);
				
				logger.info("Chrome Launched !!!");
			}
		}
		else if(System.getProperty("os.name").contains("Mac")){
			System.out.println(System.getProperty("os.name"));
			if(browser.equalsIgnoreCase("firefox")){
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/test/resources/executables/drivers/geckodriver");
				driver = new FirefoxDriver();
			}
			else if(browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/executables/drivers/chromedriver");
				driver = new ChromeDriver();
			}
		}
	}
	
	public void loadPropertiesFile() throws IOException{
		
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
		OR = new Properties();
		f1 = new File(System.getProperty("user.dir")+"/src/test/resources/properties/Config.properties");
		file = new FileInputStream(f1);
		OR.load(file);
		logger.info("loading config.properties");
		

	}
	
	
	
	
	
	
	
	@AfterSuite
	public static void quitBrowser() {
		if (driver != null) {
			driver.quit();
		}

	}

}
