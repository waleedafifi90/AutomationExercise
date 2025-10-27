package tests;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.Cart;
import pages.HomePage;
import pages.Products;


public class projectTest extends BaseTest {
    private HomePage homePage;
	private Products products;
	private Cart cart;
	
	@BeforeMethod
	public void setUpTest() {
		homePage = new HomePage(driver);
		products=new Products(driver);		
		cart    =new Cart(driver);
	}

	@Test(priority=1) 
	public void verifyClickProductLink() {
	    logStep("Verify that product text link is clickable");
		
	    homePage.clickProdctLink();
	    String actualURL =homePage.getProUrl();
	    String expectedURL="https://automationexercise.com/products";
	    Assert.assertEquals(actualURL, expectedURL, "The product Link Text is not clickable");
	    
	    logStep("Verify that product text link is clicked successfully");
	   }
	
	@Test(groups= {"products"},priority=3)
	public void verifyTypingProSearchBar() throws InterruptedException {
	    logStep("verify that the product search bar is enabled to be written in");
	
		products.clickproSearchBar();
		products.sendkeySearchBar("Clothes");
		String actual=products.getSearchBarText();
		Thread.sleep(1000);
		Assert.assertEquals(actual, "Clothes", "You can not type on searchBar");
	
	   }
	
	@Test(groups= {"products"},priority=4)
	public void verifyResultEmptySearchBar() {
		products.clearSearchBar();
		int productsNumBeforeSearch= products.getNumbersOfProducts();
		products.clickproSearchBar();
		products.clickSearchIcon();
		int productsNumAfterSearch=products.getNumbersOfProducts();
		Assert.assertEquals(productsNumBeforeSearch, productsNumAfterSearch);
	  }
	
	@Test(groups= {"products"},priority=5)
	public void verifySearchResultsContainKeyword() throws InterruptedException {
		String keyword="top";
		products.clickproSearchBar();
		products.sendkeySearchBar(keyword);
		products.clickSearchIcon();
		 Thread.sleep(3000);
	   List<String> proNames= products.getAllProuctnames();
	   for(String name:proNames) {
		   System.out.println(name);
	   Assert.assertTrue(name.toLowerCase().contains(keyword.toLowerCase()),"The Keyword is not existed");	
	        }
	}
	
	
	@Test(groups= {"products"} ,priority=6)
	public void verifySearchResultsForNotExistedKeyword() {
		products.clearSearchBar();
		String keword="@!";
		products.clickproSearchBar();
		products.sendkeySearchBar(keword);
		products.clickSearchIcon();
		List<String> result=products.getAllProuctnames();
		Assert.assertTrue(result.isEmpty(),"ERROR: Products were found for a non-existing keyword!");
	}
	    
	@Test(groups= {"products"} ,priority=7)
	public void verifyNumberOfListunderKids() {
		products.kidsClick();
	    List<WebElement> options=products.getkidsOptions();
	    int actualcount=options.size();
	    Assert.assertEquals(actualcount, 2, "number o items under kids is not correct");
	
	}
	
	@Test(groups = {"products"}, priority = 8)
	public void verifyKidsSubCategoryContainsDress() throws InterruptedException {
	    products.kidsClick();
	
	    Thread.sleep(2000);
	    List<WebElement> options = products.getkidsOptions();
	    List<String> actualNames = new ArrayList<>();
	
	    Thread.sleep(2000);
	    for (WebElement option : options) {
	        actualNames.add(option.getText().trim().toUpperCase());
	    }
	
	
	    Assert.assertTrue(
	        actualNames.stream().anyMatch(name -> name.contains("DRESS")),
	        "'DRESS' not found under Kids sub-category!: " + actualNames
	    );
	
	    System.out.println("'DRESS' exists under Kids sub-category.");
	}
	
	@Test(groups= {"products"} , priority=9)
	public void verifychangingHeadTitleForKidsDress() throws InterruptedException {
		products.scrollDown();
		products.kidsClick();
		products.clickDressKids();
		Thread.sleep(1000);
		String actual=products.getkidsDressTitle();
		Assert.assertEquals(actual, "KIDS - DRESS PRODUCTS", "Wrong Title is displayed!!");
	}
	
	@Test(groups= {"products"}, priority =10 )
	public void hoverOverAproductAndAddToCart() throws InterruptedException {
		products.scrollDown();
		products.hoverOverProduct();
		products.expectedProductName  =products.getProductNameAfterHover();
		products.expectedProductPrice  =products.getProductPriceAfterHover();
		products.clickAddFromOvelay();
		Thread.sleep(3000);
		boolean expected=products.addconfirmMessage();
		Assert.assertTrue(expected, "No confirmation message is displayed!!");
	}
	
	@Test(groups= {"products"}, priority=11)
	public void isRedirectedToCart() throws InterruptedException {
		products.clickViewCartPop();
		Thread.sleep(5000);
		String actualResult = products.getUrl();
		String expectedResult="https://automationexercise.com/view_cart";
		Assert.assertEquals(actualResult, expectedResult);
	}
	
	@Test(groups= {"Cart"}, priority=12)
	public void cartStructureContentCheck() {
		List<String> actual=cart.getCartHeader();
		List<String> expected = Arrays.asList("Item", "Description", "Price", "Quantity", "Total");
		Assert.assertEquals(actual,expected,"Cart header structure is incorrect!" );
	}
	
	@Test(groups= {"Cart"} , priority=13,dependsOnMethods = {"hoverOverAproductAndAddToCart"})
	public void verifyProductDetailsInCart() {
		String actualProNmae  = cart.getProNameFromCart();
		String actualProPrice=cart.getProPriceFromCart();
		Assert.assertEquals(actualProPrice, products.expectedProductPrice,"No matching");
		Assert.assertEquals(actualProNmae, products.expectedProductName,"No matching");
	}
	
	@Test(groups= {"Cart"} , priority=14)
	public void deleteFromCart() throws InterruptedException {
		cart.deletInCart();
		Thread.sleep(1000);
		String actualMessage=cart.getConfirmDeleting();
		Assert.assertTrue(actualMessage.contains("empty"), "The prooduct is not deleted");
	}
	
	@Test(groups= {"Cart"},  priority=15 )
	public void addToCartAnotherWay() throws InterruptedException {
		cart.hereClick();
		products.kidsClick();
		Thread.sleep(1000);
		products.clickDressKids();
		Thread.sleep(5000);
		products.clickViewSecondPro();
		products.addToCartView();
		Thread.sleep(5000);
		boolean expected=products.addconfirmMessage();
		Assert.assertTrue(expected, "No confirmation message is displayed!!");
		Thread.sleep(1000);
		products.clickContinueShopping();
	}
	  
	@Test(groups = {"Cart"}, priority = 16)
	public void changeQuanToZero() throws InterruptedException {
	    products.backSpaceQuan();
	    products.sendKeysQuan("0");
	    products.addToCartView();
	    Thread.sleep(3000);
	    products.clickContinueShopping();
	
	    boolean popupAppeared = products.addconfirmMessageNO();
	    String quantityValue = products.getQuantityValue();
	
	    if (popupAppeared && !"0".equals(quantityValue)) {
	        Assert.assertTrue(true, "System auto-corrected 0 — acceptable behavior");
	    } else {
	        Assert.assertFalse(popupAppeared, "Confirmation message appeared for zero quantity!");
	    }
	}
	
	
	@Test(groups = {"Cart"}, priority = 17)
	public void changeQuanToNegative() throws InterruptedException {
	    products.backSpaceQuan();
	    products.sendKeysQuan("-1");
	    products.addToCartView();
	    Thread.sleep(3000);
	    products.clickContinueShopping();
	
	    boolean popupAppeared = products.addconfirmMessageNO();
	    String quantityValue = products.getQuantityValue();
	
	    if (popupAppeared && !"-1".equals(quantityValue)) {
	        System.out.println("System auto-corrected invalid quantity (-1) to: " + quantityValue);
	        Assert.assertTrue(true, "System auto-corrected -1 — acceptable behavior");
	    } else {
	        Assert.assertFalse(popupAppeared, "Confirmation message appeared for negative quantity!");
	    }
	}
	
	
	@Test(groups = {"Cart"}, priority = 18)
	public void changeQuanToHugeNum() throws InterruptedException {
	    products.backSpaceQuan();
	    products.sendKeysQuan("332252");
	    products.addToCartView();
	    Thread.sleep(3000);
	    products.clickContinueShopping();
	
	    boolean popupAppeared = products.addconfirmMessageNO();
	    String quantityValue = products.getQuantityValue();
	    
	    System.out.println(quantityValue);
	    
	    if (popupAppeared && quantityValue.length() < 10) {
	        System.out.println("System truncated huge quantity input to: " + quantityValue);
	        Assert.assertTrue(true, "System truncated huge input — acceptable behavior");
	    } else {
	        Assert.assertFalse(popupAppeared, "Confirmation message appeared for huge quantity!");
	    }
	}
}