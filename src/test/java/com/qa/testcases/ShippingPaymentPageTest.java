
package com.qa.testcases;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qa.base.TestBase;
import com.qa.pages.LoginPage;
import com.qa.pages.MyAddressPage;
import com.qa.pages.ShippingPaymentPage;
import com.qa.util.TestUtil;

public class ShippingPaymentPageTest extends TestBase {

	LoginPage loginPage;
	TestUtil testUtil;
	ShippingPaymentPage shippingPage;
	MyAddressPage myAddressPage;

	public ShippingPaymentPageTest() {
		super();

	}

	@BeforeMethod
	public void setUp() throws InterruptedException {
		initialization();
		loginPage = new LoginPage();
		myAddressPage = new MyAddressPage();
		shippingPage = new ShippingPaymentPage();
		loginPage.login(prop.getProperty("username"), prop.getProperty("password"));

	}

	@Test
	public void verifyShippingPayment() throws InterruptedException, IOException {
		myAddressPage.clickOnMyAddress();
		String myAddressPageTitle = myAddressPage.verifyMyAddressPageTitle();
		Assert.assertEquals(myAddressPageTitle, "Addresses - My Store", "Addresses page title not matched");
		myAddressPage.deleteExistingAddress();
		myAddressPage.addNewAddressDetails();
		Assert.assertTrue(shippingPage.verifyWomenLabel());
		shippingPage.selectDressesFromMenu("Summer Dresses");
		shippingPage.contineShoppingAndPayment();
		Assert.assertTrue(loginPage.isSignOut());

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
