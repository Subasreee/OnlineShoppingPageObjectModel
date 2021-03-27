package com.qa.pages;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import com.qa.base.TestBase;
import com.qa.util.TestUtil;

public class MyAddressPage extends TestBase {
	TestUtil testUtil = new TestUtil();
	@FindBy(xpath = "//*[@title='Addresses']")
	WebElement btnMyAddress;

	@FindBy(xpath = "//span[text()='Add a new address']")
	WebElement btnAddNewAddress;

	@FindBy(xpath = "//input[@name='firstname']")
	WebElement firstName;
	@FindBy(xpath = "//input[@name='lastname']")
	WebElement lastName;
	@FindBy(xpath = "//input[@name='address1']")
	WebElement address1;
	@FindBy(xpath = "//input[@name='city']")
	WebElement city;
	@FindBy(xpath = "//select[@id='id_state']")
	WebElement state;
	@FindBy(xpath = "//input[@name='postcode']")
	WebElement zipPostcode;
	@FindBy(xpath = "//input[@name='phone']")
	WebElement homePhoneNum;
	@FindBy(xpath = "//input[@name='phone_mobile']")
	WebElement mobilePhoneNum;
	@FindBy(xpath = "//button[@id='submitAddress']")
	WebElement btnSave;
	@FindBy(xpath = "//*[@title='Delete']")
	WebElement btndelete;
	@FindBy(xpath = "//*[@title='Update']")
	WebElement btnUpdate;

	// Initializing the Page Objects:
	public MyAddressPage() {
		PageFactory.initElements(driver, this);
	}

	public String verifyMyAddressPageTitle() {
		return driver.getTitle();
	}

	public void clickOnMyAddress() {
		if (btnMyAddress.isDisplayed()) {
			btnMyAddress.click();
		} else {
			Assert.fail("My Address not displayed");
		}

	}

	public void addNewAddressDetails() {
		if (btnAddNewAddress.isDisplayed()) {
			btnAddNewAddress.click();
			firstName.clear();
			firstName.sendKeys(testUtil.getData("FirstName"));
			lastName.clear();
			lastName.sendKeys(testUtil.getData("LastName"));
			address1.sendKeys(testUtil.getData("Address"));
			city.sendKeys(testUtil.getData("City"));
			selectStateFromDropdownValue(testUtil.getData("State"));
			zipPostcode.sendKeys(testUtil.getData("PostalCode"));
			homePhoneNum.sendKeys(testUtil.getData("HomePhone"));
			mobilePhoneNum.sendKeys(testUtil.getData("MobilePhone"));
			btnSave.click();
			if (!btnUpdate.isDisplayed()) {
				Assert.fail("New Address not added");
			}
		} else {
			Assert.fail("Add New Address not displayed");
		}

	}

	public void selectStateFromDropdownValue(String drpvalue) {
		Select select = new Select(state);
		List<WebElement> dropdown = select.getOptions();
		for (WebElement dropdownvalues : dropdown) {
			if (dropdownvalues.getText().equals(drpvalue)) {
				dropdownvalues.click();
				break;
			}
		}
	}

	public void deleteExistingAddress() {
		if (btndelete.isDisplayed()) {
			btndelete.click();
			driver.switchTo().alert().accept();
		}
		else {
			Assert.fail();
		}
	}

}
