package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By cartTable = By.id("cart_info_table");
    private By productNames = By.xpath("//table[@id='cart_info_table']//td[@class='cart_description']//a");
    private By productPrices = By.xpath("//table[@id='cart_info_table']//td[@class='cart_price']/p");
    private By productQuantities = By.xpath("//table[@id='cart_info_table']//td[@class='cart_quantity']/button");
    private By productTotals = By.xpath("//table[@id='cart_info_table']//td[@class='cart_total']/p");
    private By proceedToCheckoutBtn = By.xpath("//a[contains(text(),'Proceed To Checkout')]");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isCartTableVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
        return driver.findElement(cartTable).isDisplayed();
    }

    public List<WebElement> getProductNames() {
        return driver.findElements(productNames);
    }

    public List<WebElement> getProductPrices() {
        return driver.findElements(productPrices);
    }

    public List<WebElement> getProductQuantities() {
        return driver.findElements(productQuantities);
    }

    public List<WebElement> getProductTotals() {
        return driver.findElements(productTotals);
    }

    public boolean isProductInCart(String expectedName) {
        for (WebElement item : getProductNames()) {
            if (item.getText().trim().equalsIgnoreCase(expectedName.trim())) {
                return true;
            }
        }
        return false;
    }

    public String getProductPrice(String productName) {
        List<WebElement> names = getProductNames();
        List<WebElement> prices = getProductPrices();
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().trim().equalsIgnoreCase(productName.trim())) {
                return prices.get(i).getText().trim();
            }
        }
        return null;
    }

    public String getProductQuantity(String productName) {
        List<WebElement> names = getProductNames();
        List<WebElement> quantities = getProductQuantities();
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().trim().equalsIgnoreCase(productName.trim())) {
                return quantities.get(i).getText().trim();
            }
        }
        return null;
    }

    public String getProductTotal(String productName) {
        List<WebElement> names = getProductNames();
        List<WebElement> totals = getProductTotals();
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getText().trim().equalsIgnoreCase(productName.trim())) {
                return totals.get(i).getText().trim();
            }
        }
        return null;
    }
}
