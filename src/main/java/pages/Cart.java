package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cart {
	
	private WebDriver driver;
//constructor
	public Cart(WebDriver driver) {
		this.driver=driver;
	}
//Locator
	
	private By cartHeader =         By.xpath("//tr[@class='cart_menu']/td");
	private By prodDescription =    By.xpath("//td[@class='cart_description']//a");
	private By productPrice     =   By.xpath("//td[@class='cart_price']");
    private By productDelete    =   By.xpath("//a[@class='cart_quantity_delete']");
    private By hereToBackProducts = By.xpath("//*[text()='here']");
    private By afterDeleteMassege = By.xpath("//p[@class='text-center']//b[contains(text(),'Cart is empty!')]");

//methods 
    
//to get all cart header content
public List<String> getCartHeader() {
	List<WebElement> headcontents=driver.findElements(cartHeader);
	List<String> cartContent=new ArrayList<>();
	
	for(WebElement headcontent:headcontents) {
		String text =headcontent.getText().trim();
		if(!text.isEmpty()) {
		cartContent.add(text);
		
		System.out.println(text);
	}
	
}
	return cartContent;
   }

// to get product name from cart
public String getProNameFromCart() {
	String productNameInCart=driver.findElement(prodDescription).getText();
	return productNameInCart;
}



//to get product price from cart
public String getProPriceFromCart() {
	String productPriceInCart=driver.findElement(productPrice).getText();
	return productPriceInCart;
}

//to delete from cart
public void deletInCart(){
	WebElement de=driver.findElement(productDelete);
	Actions hoverD=new Actions(driver);
    hoverD.moveToElement(de).perform();
    de.click();
   
}

//message delete confirmation
public String getConfirmDeleting() {
	String messageDelete = driver.findElement(afterDeleteMassege).getText();
	return messageDelete;
}

//To Click"here" to go to product
public void hereClick() {
	driver.findElement(hereToBackProducts).click();
}


}


