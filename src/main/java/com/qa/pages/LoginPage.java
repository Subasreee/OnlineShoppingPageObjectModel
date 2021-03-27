package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.qa.base.TestBase;

public class LoginPage extends TestBase {

	@FindBy(xpath = "//a[contains(text(),'Sign in')]")
	WebElement signinBtn;

	@FindBy(name = "email")
	WebElement username;

	@FindBy(name = "passwd")
	WebElement password;

	@FindBy(xpath = "//button[@id='SubmitLogin']")
	WebElement loginBtn;
	@FindBy(xpath = "//a[@title='Log me out']")
	WebElement sigoutbtn;


	// Initializing the Page Objects:
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	public String validateLoginPageTitle() {
		return driver.getTitle();
	}

	public MyAddressPage login(String UN, String PWD) throws InterruptedException {
		signinBtn.click();
		username.sendKeys(UN);
		password.sendKeys(PWD);
		loginBtn.click();
		if (!sigoutbtn.isDisplayed()) {
			Assert.fail();
		}

		return new MyAddressPage();
	}

	public boolean isSignOut() {
		if (sigoutbtn.isDisplayed()) {
			sigoutbtn.click();
			if (signinBtn.isDisplayed()) {
				return true;
			}

		} else {
			Assert.fail();

		}
		return false;
	}

}
