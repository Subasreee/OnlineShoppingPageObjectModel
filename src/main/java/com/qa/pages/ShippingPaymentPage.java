package com.qa.pages;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qa.base.TestBase;

public class ShippingPaymentPage extends TestBase {

	@FindBy(xpath = "//*[@class='sf-with-ul' and text()='Women']")
	WebElement womenTab;

	@FindBy(xpath = "//div[@id='categories_block_left']//a[contains(@title,'choice of evening, casual or summer dresses!')]")
	WebElement expandDresses;

	@FindBy(xpath = "(//img[@title='Printed Summer Dress'])[last()-1]")
	WebElement selectSummerdress;

	@FindBy(xpath = "//span[text()='Add to cart']")
	WebElement addtocart;

	@FindBy(xpath = "//*[@class='icon-plus']")
	WebElement iconquantity;

	@FindBy(xpath = "//input[@id='quantity_wanted']")
	WebElement quantityfield;

	@FindBy(xpath = "//select[@id='group_1']")
	WebElement selectsize;

	@FindBy(xpath = "//*[@class='color_pick selected']")
	WebElement colorpicked;

	@FindBy(xpath = "//*[@title='Continue shopping']")
	WebElement btncontinueshopping;

	@FindBy(xpath = "//div[@class='breadcrumb clearfix']//*[@title='Summer Dresses']")
	WebElement labelSummerDress;

	@FindBy(xpath = "(//*[contains(text(),'Proceed to checkout')])[last()]")
	WebElement proceedCheckout;

	@FindBy(xpath = "//*[@title='Pay by bank wire']")
	WebElement payBank;

	@FindBy(xpath = "//*[@id='uniform-cgv']//span")
	WebElement agreeTerm;
	@FindBy(xpath = "//span[@id='view_full_size']//img")
	WebElement bigpic;

	@FindBy(xpath = "//a[@title='Close']")
	WebElement btnclose;
	@FindBy(xpath = "//iframe[@class='fancybox-iframe']")
	WebElement iframe;

	@FindBy(xpath = "//*[contains(text(),'I confirm my order')]")
	WebElement confirmOrder;
	@FindBy(xpath = "//*[@title='Back to orders']")
	WebElement backOrder;
	@FindBy(xpath = "(//*[text()='Order history'])[last()]")
	WebElement orderHistory;

	// Initializing the Page Objects:
	public ShippingPaymentPage() {
		PageFactory.initElements(driver, this);
	}

	public boolean verifyWomenLabel() {
		return womenTab.isDisplayed();
	}

	public void selectDressesFromMenu(String typedress) {
		if (womenTab.isDisplayed()) {
			womenTab.click();
			expandDresses.click();
			driver.findElement(By.xpath("//div[@id='categories_block_left']//a[contains(text(),'" + typedress + "')]"))
					.click();
		} else {
			Assert.fail();
		}

	}

	public void clickViewType(String typeview) {
		WebElement view = driver.findElement(By.xpath("//*[@title='" + typeview + "']"));
		if (view.isDisplayed()) {
			view.click();
		} else {
			Assert.fail("Element not present");
		}

	}

	public void addQuantity(int nofquantity) {
		for (int i = 1; i < nofquantity; i++) {
			if (iconquantity.isDisplayed()) {
				iconquantity.click();
			}
		}
		if (!quantityfield.getAttribute("value").equals(Integer.toString(nofquantity))) {
			Assert.fail("Given quantity not Addedd");
		}

	}

	public void selectSize(int size) {
		Select select = new Select(selectsize);
		select.selectByIndex(size);
	}

	public void selectColor(String dressColor) {
		WebElement view = driver.findElement(By.xpath("//*[@title='" + dressColor + "']"));
		view.click();
		if (!colorpicked.isDisplayed()) {
			Assert.fail();
		}

	}

	public void contineShoppingAndPayment() throws InterruptedException, IOException {
		clickViewType("List");
		for (int i = 1; i <= 3; i++) {
			WebElement view = driver.findElement(By.xpath("(//div[@class='product-image-container'])[" + i + "]"));
			Actions action = new Actions(driver);
			action.moveToElement(view).build().perform();
			WebElement quickview = driver.findElement(By.xpath("(//span[text()='Quick view'])[" + i + "]"));
			quickview.click();
			Thread.sleep(1000);
			driver.switchTo().frame(iframe);
			if (isElementPresent(bigpic)) {
				bigpic.click();
				driver.switchTo().defaultContent();
			}
			addQuantity(5);
			selectSize(2);
			selectColor("Yellow");
			addtocart.click();
			if (i == 3) {
				proceedToCheckOut();
				Assert.assertTrue(confirmOrderAndPayment(), "Ordered History Not displayed");
			} else {
				btncontinueshopping.click();
				labelSummerDress.click();
			}

		}

	}

	public void proceedToCheckOut() {
		do {
			proceedCheckout.click();
		} while (!isElementVisible(agreeTerm));
		agreeTerm.click();
		proceedCheckout.click();
	}

	public boolean isElementPresent(WebElement element) {

		try {
			if (element != null) {
				return true;
			}
			return false;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public boolean isElementVisible(WebElement element) {

		try {
			if (element.isDisplayed()) {
				return true;
			}
			return false;
		}
		catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean confirmOrderAndPayment() throws IOException, InterruptedException {
		boolean isOrderHistory = false;

		if (isElementPresent(payBank)) {
			payBank.click();
			confirmOrder.click();
			Thread.sleep(1000);
			takeScreenshotAtEndOfTest("OrderConfirmation");
			backOrder.click();
			if (orderHistory.isDisplayed()) {
				takeScreenshotAtEndOfTest("OrderHistory");
				isOrderHistory = true;
			}
		} else {
			Assert.fail();
		}
		return isOrderHistory;

	}

	public static void takeScreenshotAtEndOfTest(String fileName) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + fileName + ".png"));
	}

}
