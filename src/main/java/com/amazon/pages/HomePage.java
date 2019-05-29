package com.amazon.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.amazon.helper.Logger.LoggerHelper;
import com.amazon.testBase.Config;
import com.amazon.testBase.TestBase;
import com.goa.helper.Wait.WaitHelper;



public class HomePage  extends TestBase   {
	
	private final Logger log = LoggerHelper.getLogger(HomePage.class);
	WaitHelper waitHelper;

	public String firstproduct;
	public String SecondProduct;
	
	@FindBy(id="nav-logo")
	public WebElement landingpage;
	
	@FindBy(id="twotabsearchtextbox")
	public WebElement SearchTextbox;
	
	@FindBy(xpath="//span[contains(text(),'Aqua J3 (1.8 inch Display')]")
	public List<WebElement> ProductList;
	
	
	
	
	@FindBy(id="add-to-cart-button")
	public WebElement addtoCart;
	
	@FindBy(id="hlb-view-cart-announce")
	public WebElement cartbutton;
	
	@FindBy(xpath="//*[starts-with(@id,'sc-item')]/div[4]/div/div[1]/div/div/div[2]/ul/li[1]/span/a/span")
	public WebElement  productafterAddingCart;
	
	
	
	
	@FindBy(xpath="//*[@id=\"gutterCartViewForm\"]/div[3]/div/div/div[1]/p/span/span[2]/span")
	public WebElement  productprice;
	@FindBy(xpath="//*[starts-with(@id,'sc-item')]/div[4]/div/div[3]/div/div/span/span/span/span")
	public WebElement  quantityDropdown;
	
	@FindBy(id="dropdown1_9")
	public WebElement  clickingDropdown;
	
	@FindBy(xpath="//*[starts-with(@id,'sc-item')]/div[4]/div/div[3]/div/div/input")
	public WebElement  enterProductNumber;
	
	
	
	@FindBy(xpath="//*[starts-with(@id,'sc-item')]/div[4]/div/div[3]/div/div/div/span/span/a")
	public WebElement  updateButton;
	
	@FindBy(xpath="//div[@class='a-box-inner']//*[contains(text(),'Subtotal (10 items):')]/following-sibling::span")
	public WebElement  Multproductprice;
	
	@FindBy(xpath="//span[contains(text(),'Mi Redmi 6A')]")
	public List<WebElement> SecondProductList;
	
	@FindBy(xpath="//div[@class='sc-quantity-update-message a-spacing-top-mini']")
	public WebElement  CheckingUpdateAlert;
	
	
	@FindBy(xpath="//*[starts-with(@id,'sc-item')]/div[4]/div/div[1]/div/div/div[2]/ul/li[1]/span/a/span")
	public List<WebElement> ValidatingProductinList;
	
	@FindBy(xpath="//*[starts-with(@aria-label,'Delete Mi Redmi 6A')]")
	public WebElement  DeleteSecondProduct;

	
	
	
	
	public HomePage() {
		
		PageFactory.initElements(driver, this);
		
		waitHelper = new WaitHelper(driver);
			waitHelper.waitForElement(driver, landingpage,new Config(TestBase.OR).getExplicitWait());
		}
	
	public void enterProductName(String productname) throws InterruptedException {
		log.info("Searching product Name.... "+productname);
		waitHelper.waitForElement(driver, SearchTextbox,new Config(TestBase.OR).getExplicitWait());
		SearchTextbox.sendKeys(productname);
		SearchTextbox.sendKeys(Keys.DOWN);
		SearchTextbox.sendKeys(Keys.RETURN);
		
	}
	
	public void verifylist() {
		log.info("Verifying Product List.... ");
		int productcount=ProductList.size();
		System.out.println("The number of products are "+productcount );
		
		for(int j=0;j<ProductList.size();j++) {
			if(ProductList.get(j).getText().contains("Aqua J3")) {
				
				System.out.println("The product name is :"+ProductList.get(j).getText());
				
			}else {
				System.out.println("Products not available");
			}
			
		}
		
	}
	
	public void clickonFirstProduct() throws InterruptedException {
		
		log.info("Clicking on First Product.... ");
		if(ProductList.get(0).isDisplayed()) {
			
			ProductList.get(0).click();
			 firstproduct=ProductList.get(0).getText();
			System.out.println("The first product is :"+firstproduct);
		}
			String mainWindow=driver.getWindowHandle();
			 // It returns no. of windows opened by WebDriver and will return Set of Strings
			 Set<String> set =driver.getWindowHandles();
			 // Using Iterator to iterate with in windows
			 Iterator<String> itr= set.iterator();
			 while(itr.hasNext()){
			 String childWindow=itr.next();
			    // Compare whether the main windows is not equal to child window. If not equal, we will close.
			 if(!mainWindow.equals(childWindow)){
			 driver.switchTo().window(childWindow);
			 System.out.println("New Window Title is: "+driver.switchTo().window(childWindow).getTitle());
			 Thread.sleep(5000);
	        }
			 
			 }
	}
	
	public void addtoCart() throws InterruptedException {
		log.info("Adding to Cart.... ");
		Thread.sleep(5000);
		addtoCart.click();
	
		waitHelper.waitForElement(driver, cartbutton,new Config(TestBase.OR).getExplicitWait());
		cartbutton.click();
		String productNameinCart=productafterAddingCart.getText();
		Assert.assertEquals(firstproduct, productNameinCart);
		
	
}
	
	public void updatingtheProductsinCart() throws InterruptedException {
		log.info("Updating Product count to 10.... ");
		String singleproductprice =productprice.getText();
		//int result = Integer.parseInt(singleproductprice);	
		double singelproductpr=Double.parseDouble(singleproductprice);  
		System.out.println("The Single Product Price"+singelproductpr);
		
		quantityDropdown.click();
		clickingDropdown.click();
		Thread.sleep(5000);
		
		enterProductNumber.sendKeys("10");
		updateButton.click();
		Thread.sleep(5000);
		try {
		if(CheckingUpdateAlert.isEnabled())
			System.out.println("Seller has a a limit, So cannot order 10 products");
		} catch(Exception e) {
		double TenProductExpectedPrice =singelproductpr * 10;
		System.out.println("value after multiplying"+TenProductExpectedPrice);
		Thread.sleep(5000);
		String TenproductsDisplayprice=Multproductprice.getText();
		
		TenproductsDisplayprice=TenproductsDisplayprice.replace(",", "");
		double TenProductActualPrice=Double.parseDouble(TenproductsDisplayprice);  
		System.out.println("Ten Product Actual Price"+ TenProductActualPrice);
		
		Assert.assertEquals(TenProductExpectedPrice,TenProductActualPrice,"Prices are matching");
		}
	}
	
	public void SearchingSecondProduct(String SecondProduct) {
		log.info("Searching product Name.... "+SecondProduct);
		waitHelper.waitForElement(driver, SearchTextbox,new Config(TestBase.OR).getExplicitWait());
		SearchTextbox.sendKeys(SecondProduct);
		SearchTextbox.sendKeys(Keys.DOWN);
		SearchTextbox.sendKeys(Keys.RETURN);
		
	}
	
	public void verifySecondProductlist() {
		
		int productcount=SecondProductList.size();
		System.out.println("The number of products are "+productcount );
		
		for(int j=0;j<SecondProductList.size();j++) {
			if(SecondProductList.get(j).getText().contains("Mi Redmi 6A")) {
				
				System.out.println("The product name is :"+SecondProductList.get(j).getText());
				
			}else {
				System.out.println("Products not available");
			}
			
		}
		
	}
public void clickonSecondProduct() throws InterruptedException {
	log.info("Clicking on Second Product.... ");
		
		if(SecondProductList.get(0).isDisplayed()) {
			
			SecondProductList.get(0).click();
			SecondProduct=SecondProductList.get(0).getText();
			System.out.println("The first product is :"+SecondProduct);
		}
			String mainWindow=driver.getWindowHandle();
			 // It returns no. of windows opened by WebDriver and will return Set of Strings
			 Set<String> set =driver.getWindowHandles();
			 // Using Iterator to iterate with in windows
			 Iterator<String> itr= set.iterator();
			 while(itr.hasNext()){
			 String childWindow=itr.next();
			    // Compare whether the main windows is not equal to child window. If not equal, we will close.
			 if(!mainWindow.equals(childWindow)){
			 driver.switchTo().window(childWindow);
			 System.out.println("New Window Title is: "+driver.switchTo().window(childWindow).getTitle());
			 Thread.sleep(5000);
	        }
			 
			 }
	}

public void addSecondProducttoCart() throws InterruptedException {
	log.info("Adding Second Product to Cart .... ");
	Thread.sleep(5000);
	addtoCart.click();

	waitHelper.waitForElement(driver, cartbutton,new Config(TestBase.OR).getExplicitWait());
	cartbutton.click();
	
	String SecondProductinCart=ValidatingProductinList.get(0).getText();
	Assert.assertEquals(SecondProduct, SecondProductinCart,"Product is present in cart");
	

}

public void deleteSecondProduct() throws InterruptedException {
	log.info("Deleting Second Product.... ");
	DeleteSecondProduct.click();
	Thread.sleep(5000);
	for(int i=0;i<ValidatingProductinList.size();i++) {
		if(ValidatingProductinList.get(i).getText().contains("Mi Redmi 6A")) {
			System.out.println("Product is still exist");
		}else {
			System.out.println("Product is Deleted");
		}
	}
	
	
}
}
