package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import base.BaseTest;

public class OrderTests extends BaseTest {
	@Test
	public void OrderTest() throws InterruptedException {
		logStep("Verify the order checkout");
		logStep("Navigate to Login page");
		driver.findElement(By.cssSelector("a[href='/login']")).click();
		logStep("Fill the name input");
	    driver.findElement(By.cssSelector("input[data-qa='signup-name']")).sendKeys("Kittaneh");
	    String email = "Mohammad.Husam" + System.currentTimeMillis() + "@Axsos.com";
	    logStep("Fill the Email input");
	    driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(email);
	    logStep("Click signup button");
	    driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();
	    logStep("Select the Gender");
	    driver.findElement(By.id("id_gender1")).click();
	    logStep("Fill the password");
	    driver.findElement(By.id("password")).sendKeys("Kittaneh004");
	    logStep("Fill birth day");
	    driver.findElement(By.id("days")).sendKeys("29");
	    logStep("Fill birth month");
	    driver.findElement(By.id("months")).sendKeys("September");
	    logStep("Fill birth year");
	    driver.findElement(By.id("years")).sendKeys("2021");
	    logStep("Fill Firstname input");
	    driver.findElement(By.id("first_name")).sendKeys("Mohammad");
	    logStep("Fill Lastname input");
	    driver.findElement(By.id("last_name")).sendKeys("Kittaneh");
	    logStep("Fill Company input");
	    driver.findElement(By.id("company")).sendKeys("QA Axsos");
	    logStep("Fill Address1 input");
	    driver.findElement(By.id("address1")).sendKeys("123 Test Street");
	    logStep("Fill Address2 input");
	    driver.findElement(By.id("address2")).sendKeys("Tulkarm");
	    logStep("Fill the Country input");
	    driver.findElement(By.id("country")).sendKeys("United States");
	    logStep("Fill the State input");
	    driver.findElement(By.id("state")).sendKeys("Palestine");
	    logStep("Fill the City input");
	    driver.findElement(By.id("city")).sendKeys("Tulkarm");
	    logStep("Fill the Zipcode input");
	    driver.findElement(By.id("zipcode")).sendKeys("p2001");
	    logStep("Fill the Mobile  number input");
	    driver.findElement(By.id("mobile_number")).sendKeys("0568284298");
	    logStep("Click Create account button");
	    driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();

	    System.out.println("Account Created Successfully!");
	    logStep("Click Continue button");
	    driver.findElement(By.cssSelector("a[data-qa='continue-button']")).click();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    
	    try {
	    	logStep("Expand Kids category");
	        WebElement kidsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='#Kids']")));
	        kidsLink.click();
	        System.out.println("expanded opened");
	    	logStep("Click sub-category dress");
	        WebElement dressLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='Kids']//a[contains(text(), 'Dress')]")));
	        dressLink.click();
	    }
	    catch (org.openqa.selenium.TimeoutException e) {
	        System.out.println(" The 'Kids' link was not found ");
	        
	    }
	    

	    Thread.sleep(1000);
    	logStep("Click Add to cart button");
	    driver.findElement(By.cssSelector(".add-to-cart")).click();
	    Thread.sleep(1000);
//	    driver.findElement(By.cssSelector("button.close-modal.btn.btn-success")).click();
    	logStep("Click View cart button");
	    driver.findElement(By.xpath("//div[@id='cartModal']//a[@href='/view_cart']")).click();
	    Thread.sleep(1000);
	    System.out.println("Opened Cart Page");
    	logStep("Click Checkout button");
	    driver.findElement(By.cssSelector("a.check_out")).click();
	    System.out.println("Proceeded to Checkout Page");
	    driver.findElement(By.xpath("//a[contains(@class, 'check_out')]")).click();
    	logStep("Fill the name from the Payment page");
	    driver.findElement(By.cssSelector(".form-control")).sendKeys("Mohammad");
	    logStep("Fill the CC from the Payment page");
	    driver.findElement(By.cssSelector(".card-number")).sendKeys("1234 5678 9012 3456");
	    logStep("Fill the CCV from the Payment page");
	    driver.findElement(By.cssSelector(".card-cvc")).sendKeys("779");
	    logStep("Fill the Expiration month from the Payment page");
	    driver.findElement(By.cssSelector(".form-control.card-expiry-month")).sendKeys("09");
	    logStep("Fill the Expiration year from the Payment page");
	    driver.findElement(By.cssSelector(".card-expiry-year")).sendKeys("28");
    	logStep("Click Submit button");
	    driver.findElement(By.cssSelector(".form-control.btn.btn-primary.submit-button")).click();
	    //driver.quit();
	}
}
