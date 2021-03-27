package com.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.LoginPage;

public class LoginPageTest extends TestBase {
	LoginPage loginPage;
	public LoginPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() {
		initialization();
		loginPage = new LoginPage();
	}

	@Test
	public void loginPageTitleTest() throws InterruptedException {
		loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		String title = loginPage.validateLoginPageTitle();
		Assert.assertEquals(title, "My account - My Store");
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
