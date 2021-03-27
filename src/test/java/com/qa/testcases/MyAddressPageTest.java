package com.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.LoginPage;
import com.qa.pages.MyAddressPage;
import com.qa.util.TestUtil;

public class MyAddressPageTest extends TestBase {
	LoginPage loginPage;
	MyAddressPage myAddressPage;
	TestUtil testUtil;
	

	public MyAddressPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		myAddressPage = new MyAddressPage();
		loginPage = new LoginPage();

	}

	@Test
	public void VerifyAddNewAddress() throws InterruptedException {
		myAddressPage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		myAddressPage.clickOnMyAddress();
		String myAddressPageTitle = myAddressPage.verifyMyAddressPageTitle();
		Assert.assertEquals(myAddressPageTitle, "Addresses - My Store", "Addresses page title not matched");
		myAddressPage.deleteExistingAddress();
		myAddressPage.addNewAddressDetails();

	}

	

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
