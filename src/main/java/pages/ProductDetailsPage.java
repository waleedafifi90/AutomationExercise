package pages;

import org.openqa.selenium.*;
import java.util.*;

public class ProductDetailsPage {
    WebDriver driver;

    // Locators
    private By productName = By.cssSelector(".product-information h2");
    private By productPrice = By.cssSelector(".product-information span span");
    private By quantityInput = By.id("quantity");
    private By addToCartButton = By.cssSelector("button.cart");
    private By availability = By.xpath("//b[text()='Availability:']");
    private By condition = By.xpath("//b[text()='Condition:']");
    private By brand = By.xpath("//b[text()='Brand:']");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public String getProductName() {
        return driver.findElement(productName).getText().trim();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText().trim();
    }

    public String getQuantityValue() {
        return driver.findElement(quantityInput).getAttribute("value").trim();
    }

    public boolean isAddToCartVisible() {
        return driver.findElement(addToCartButton).isDisplayed();
    }

    public boolean areDetailsSectionsVisible() {
        return driver.findElement(availability).isDisplayed()
            && driver.findElement(condition).isDisplayed()
            && driver.findElement(brand).isDisplayed();
    }
}
