package com.amazon.testcases;

import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.amazon.helper.Logger.LoggerHelper;
import com.amazon.pages.HomePage;
import com.amazon.testBase.TestBase;
import com.amazon.utility.Utilities;


public class ProductSearchTest extends TestBase  {
	HomePage home;
	
	private final Logger log = LoggerHelper.getLogger(ProductSearchTest.class);
	@Test(dataProviderClass=Utilities.class,dataProvider="dp")
	public void productSearch(Hashtable<String ,String> data) throws InterruptedException  {
    log.info(ProductSearchTest.class.getName()+" started");
		
		
	
		if(data.get("runmode").equalsIgnoreCase("N")) {
			throw new SkipException("Skipping the testcase as the Runmode is NO");
		}
		
		home = new HomePage();
	
		home.enterProductName(data.get("productname"));
		home.verifylist();
		home.clickonFirstProduct();
		home.addtoCart();
		home.updatingtheProductsinCart();
		home.SearchingSecondProduct(data.get("secondProduct"));
		home.clickonSecondProduct();
		home.addSecondProducttoCart();
		home.deleteSecondProduct();
		
	} 
	
}
