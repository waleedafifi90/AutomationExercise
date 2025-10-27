package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CarouselPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By carouselContainer = By.id("slider-carousel");
    private By leftArrow = By.xpath("//a[@data-slide='prev']");
    private By rightArrow = By.xpath("//a[@data-slide='next']");
    private By slides = By.xpath("//div[@id='slider-carousel']//div[contains(@class,'item')]");
    private By activeSlide = By.xpath("//div[@id='slider-carousel']//div[contains(@class,'item active')]");
    private By indicators = By.xpath("//ol[@class='carousel-indicators']/li");
    private String activeIndicatorXpath = "//ol[@class='carousel-indicators']/li[@class='active']";

    public CarouselPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isCarouselVisible() {
        WebElement carousel = wait.until(ExpectedConditions.visibilityOfElementLocated(carouselContainer));
        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", carousel);
        return carousel.isDisplayed();
    }

    public boolean isLeftArrowVisible() {
        return driver.findElement(leftArrow).isDisplayed();
    }

    public boolean isRightArrowVisible() {
        return driver.findElement(rightArrow).isDisplayed();
    }

    public void clickLeftArrow() {
        WebElement arrow = wait.until(ExpectedConditions.elementToBeClickable(leftArrow));
        arrow.click();
    }

    public void clickRightArrow() {
        WebElement arrow = wait.until(ExpectedConditions.elementToBeClickable(rightArrow));
        arrow.click();
    }

    public int getActiveSlideIndex() {
        List<WebElement> allSlides = driver.findElements(slides);
        WebElement active = driver.findElement(activeSlide);
        return allSlides.indexOf(active);
    }

    public int getActiveIndicatorIndex() {
        List<WebElement> allIndicators = driver.findElements(indicators);
        WebElement active = driver.findElement(By.xpath(activeIndicatorXpath));
        return allIndicators.indexOf(active);
    }

    public boolean waitForSlideChange(int oldIndex) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(d -> getActiveSlideIndex() != oldIndex);
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean waitForIndicatorChange(int oldIndex) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(d -> getActiveIndicatorIndex() != oldIndex);
        } catch (TimeoutException e) {
            return false;
        }
    }
}
