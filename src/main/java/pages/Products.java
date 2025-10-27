package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Products {
	
	private WebDriver driver;
	public static String expectedProductName;
	public static String expectedProductPrice;
	
	public Products(WebDriver driver) {
		this.driver=driver;
	}
	
	private By kidsbutton    =         By.xpath("//a[normalize-space()='Kids']");
	private By kidsOptions   =         By.xpath("//div[@id='Kids']//ul/li/a");
	private By dressKids=              By.xpath("//div[@id='Kids']//a[contains(text(),'Dress')]");
	private By productSearchBar=       By.xpath("//input[@id='search_product']");
	private By searchBarIcon =         By.xpath("//button[@id='submit_search']");
	private By allProductImages =      By.cssSelector(".features_items.product-image-wrapper");
	private By allproductNames=        By.cssSelector(".features_items.col-sm-4 p");
	private By productDressTitle=      By.xpath("//*[text()='Kids - Dress Products']");
	private By firstProduct     =      By.xpath("//div[@class='productinfo text-center']//p[contains(text(),'Sleeves Top and Short')]");
	private By cartaddoverlay   =      By.xpath("//div[@class='product-overlay']//a[contains(text(),'Add to cart')]");
	private By addedconfirm     =      By.xpath("//h4[normalize-space()='Added!']");
	private By viewCartPop      =      By.xpath("//p[@class='text-center']//a");
	private By productDescOvelayed =   By.xpath("//div[@class='overlay-content']/p");
	private By productPriceOverlayed = By.xpath("//div[@class='overlay-content']/h2");
	private By viewProductSecondPro  = By.xpath("(//a[contains(text(),'View Product')])[2]");
	private By addToCartViewProduct  = By.xpath("//button[normalize-space()='Add to cart']");
	private By continueShopping      = By.xpath("//button[normalize-space()='Continue Shopping']");
	private By productQuantity       = By.xpath("//input[@id='quantity']");
	
	public void scrollDown() {
		  ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 200)");
	}
	
	public void kidsClick() {
		driver.findElement(kidsbutton).click();
	}
	
	public void clickDressKids() {
	    driver.findElement(dressKids).click();
	}
	
	public String getUrl() {
		return driver.getCurrentUrl();
	}
	
	public void clickproSearchBar() {
		driver.findElement(productSearchBar).click();
	
	}

	public void clearSearchBar() {
		driver.findElement(productSearchBar).click();
		driver.findElement(productSearchBar).clear();
	}
	
	public void clickSearchIcon() {
		driver.findElement(searchBarIcon).click();	
	}
	
	public void sendkeySearchBar(String Text) {
	   WebElement search= driver.findElement(productSearchBar);  
	   search.sendKeys(Text);
	}
	
	public String getSearchBarText() {
		return driver.findElement(productSearchBar).getAttribute("value");
	}
	
	public int getNumbersOfProducts() {
		List<WebElement> proList=driver.findElements(allProductImages);
		return proList.size();
	}
	
	public List<String> getAllProuctnames(){
		List<WebElement> names =driver.findElements(allproductNames);
		List<String>proNames=new ArrayList<>();
		for(WebElement name :names) {
			proNames.add(name.getText());
		}
		return proNames;	
	}
	
	public List<WebElement>getkidsOptions(){
	    List<WebElement> options = driver.findElements(kidsOptions);
	    return options;
	}
	
	public String getkidsDressTitle() {
		return driver.findElement(productDressTitle).getText();	
	}
	
	public void hoverOverProduct() {
		 WebElement productLink=driver.findElement(firstProduct);
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));	
	wait.until(ExpectedConditions.visibilityOfElementLocated(firstProduct));
	   Actions hover2=new Actions(driver);
	   hover2.moveToElement(productLink).perform();    
	}
	
	public void clickAddFromOvelay() {
	WebDriverWait wait2=new WebDriverWait(driver,Duration.ofSeconds(10));
	wait2.until(ExpectedConditions.elementToBeClickable(cartaddoverlay));
		driver.findElement(cartaddoverlay).click();
	}
	
	public boolean addconfirmMessage() {
		return driver.findElement(addedconfirm).isDisplayed();
	}
	
	public boolean addconfirmMessageNO() {
		return !driver.findElement(addedconfirm).isDisplayed();
	}
	
	public void clickViewCartPop() {
		driver.findElement(viewCartPop).click();
	}
	
	public String getProductNameAfterHover() {
		String productOverlayName=driver.findElement(productDescOvelayed).getText();
		return productOverlayName;
	}
	
	public String getProductPriceAfterHover() {
		String productOverlayprice=driver.findElement(productPriceOverlayed).getText();
		return productOverlayprice;
	}
	
	public void clickViewSecondPro() {
		driver.switchTo().defaultContent();//to reach the content
		driver.findElement(viewProductSecondPro).click();
	}
	
	public void addToCartView() {
		driver.findElement(addToCartViewProduct).click();
	}
	
	public void clickContinueShopping() {
		driver.findElement(continueShopping).click();
	}

	public void backSpaceQuan() {
		WebElement input=driver.findElement(productQuantity);
		input.sendKeys(Keys.CONTROL+"a");
		input.sendKeys(Keys.BACK_SPACE);
	}
	
	public void sendKeysQuan(String value) {
		WebElement input2=driver.findElement(productQuantity);
		input2.sendKeys(value);
	}
	
	public String getQuantityValue() {
	    return driver.findElement(By.id("quantity")).getAttribute("value");
	}
	
	public void printingProductName() {
		System.out.println("test");
	}

}
