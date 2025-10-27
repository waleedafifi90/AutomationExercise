package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BrandsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By brandsSection = By.xpath("//div[@class='brands_products']");
    private By brandItems = By.xpath("//div[@class='brands-name']//li/a");
    private By pageHeading = By.xpath("//h2[contains(@class,'title text-center')]");
    private By breadcrumb = By.xpath("//ol[@class='breadcrumb'] | //ul[@class='breadcrumb']");

    public BrandsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void scrollToBrandsSection() {
        WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(brandsSection));
        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", section);
    }

    public Map<String, Integer> getBrandsList() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(brandItems));
        List<WebElement> brands = driver.findElements(brandItems);

        Map<String, Integer> brandMap = new LinkedHashMap<>();
        for (WebElement brand : brands) {
            String text = brand.getText().trim();
            String name = text.replaceAll("\\(.*?\\)", "").trim();
            String count = text.replaceAll("[^0-9]", "").trim();
            int countNum = count.isEmpty() ? 0 : Integer.parseInt(count);
            brandMap.put(name, countNum);
        }
        return brandMap;
    }

    public void clickBrand(String brandName) {
        By brandLocator = By.xpath("//div[@class='brands-name']//a[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + brandName.toLowerCase() + "')]");
        WebElement brandLink = wait.until(ExpectedConditions.elementToBeClickable(brandLocator));
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", brandLink);
        brandLink.click();
    }

    public boolean isBrandPageHeadingCorrect(String brandName) {
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeading));
        return heading.getText().toLowerCase().contains(brandName.toLowerCase());
    }
    
    public boolean isBreadcrumbVisible() {
        try {
            WebElement crumb = wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumb));
            return crumb.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getBreadcrumbText() {
        try {
            WebElement crumb = wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumb));
            return crumb.getText().trim();
        } catch (TimeoutException e) {
            return "Breadcrumb not found";
        }
    }

    public boolean isBreadcrumbCorrect(String expectedBrand) {
        String breadcrumbText = getBreadcrumbText().toLowerCase();
        return breadcrumbText.contains(expectedBrand.toLowerCase());
    }

}
