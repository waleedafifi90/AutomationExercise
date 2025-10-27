package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FeatureSectionPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;

    // Locators
    private By featuresSection = By.xpath("//h2[text()='Features Items']");
    private By firstProductCard = By.xpath("(//div[@class='features_items']//div[@class='product-image-wrapper'])[1]");
    private By addToCartButton = By.xpath("(//div[@class='features_items']//a[contains(text(),'Add to cart')])[1]");
    private By popupModal = By.xpath("//div[@id='cartModal']");
    private By popupMessage = By.xpath("//div[@id='cartModal']//p[contains(text(),'added to cart')]");
    private By viewCartLink = By.xpath("//div[@id='cartModal']//a//u[contains(text(),'View Cart')]");
    private By continueShoppingButton = By.xpath("//div[@id='cartModal']//button[contains(text(),'Continue Shopping')]");

    public FeatureSectionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    public void scrollToFeatureSection() {
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(featuresSection));
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", section);
    }

    public void hoverOverFirstProduct() {
        WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(firstProductCard));
        actions.moveToElement(card).perform();
    }

    public String getAddToCartButtonColor() {
        WebElement btn = driver.findElement(addToCartButton);
        return btn.getCssValue("background-color");
    }

    public String getAddToCartTextColor() {
        WebElement btn = driver.findElement(addToCartButton);
        return btn.getCssValue("color");
    }

    public String getProductCardBackground() {
        WebElement card = driver.findElement(firstProductCard);
        return card.getCssValue("background-color");
    }

    public void clickAddToCart() {
        try {
            closeAdIfVisible();

            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));

            js.executeScript("arguments[0].scrollIntoView({behavior:'instant', block:'center'});", btn);
            js.executeScript("window.scrollBy(0, -150);");

            try {
                btn.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Click intercepted, retrying with JS click...");
                js.executeScript("arguments[0].click();", btn);
            }

        } catch (Exception e) {
            System.out.println("Failed to click Add to Cart: " + e.getMessage());
        }
    }


    public boolean isPopupVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(popupModal));
        return driver.findElement(popupModal).isDisplayed();
    }

    public boolean isPopupMessageDisplayed() {
        return driver.findElement(popupMessage).isDisplayed();
    }

    public boolean isViewCartVisible() {
        return driver.findElement(viewCartLink).isDisplayed();
    }

    public boolean isContinueShoppingVisible() {
        return driver.findElement(continueShoppingButton).isDisplayed();
    }

    public void clickViewCart() {
        driver.findElement(viewCartLink).click();
    }

    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(popupModal));
    }
    
    public void closeAdIfVisible() {
        try {
            js.executeScript(
                "const ad = document.querySelector('iframe, div[style*=\"bottom\"], div[id*=\"ad\"], div[class*=\"ad\"]);" +
                "if(ad) { ad.style.display='none'; console.log('Ad banner hidden'); }"
            );
        } catch (Exception e) {
            System.out.println("No ad banner found or could not hide: " + e.getMessage());
        }
    }

}
