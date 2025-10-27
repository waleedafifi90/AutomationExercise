package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CategoryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private By categoryHeader = By.xpath("//div[@class='left-sidebar']//h2[text()='Category']");
    private By allCategories = By.xpath("//div[@class='panel-group category-products']//a");
    private String categoryXpath = "//div[@class='panel-group category-products']//a[contains(@href,'%s')]";
    private String categoryPanelXpath = "//div[@id='%s']";
    private String subCategoryXpath = "//div[@id='%s']//a[contains(text(),'%s')]";

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void scrollToCategorySection() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryHeader));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(800);
        } catch (Exception e) {
            System.out.println("Could not scroll to Category section: " + e.getMessage());
        }
    }

    public boolean isCategorySectionVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryHeader));
        return driver.findElement(categoryHeader).isDisplayed();
    }

    public void clickCategory(String categoryName) {
        By locator = By.xpath(String.format(categoryXpath, categoryName));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public boolean isCategoryExpanded(String categoryName) {
        try {
            WebElement panel = driver.findElement(By.xpath(String.format(categoryPanelXpath, categoryName)));
            String classAttr = panel.getAttribute("class");
            return !classAttr.contains("collapsed");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCategoryCollapsed(String categoryName) {
        try {
            WebElement panel = driver.findElement(By.xpath(String.format(categoryPanelXpath, categoryName)));
            String classAttr = panel.getAttribute("class");
            return classAttr.contains("collapse");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isSubCategoryVisible(String categoyName,String subCategoryName) {
        try {
            WebElement subCat = driver.findElement(By.xpath(String.format(subCategoryXpath, categoyName, subCategoryName)));
            return subCat.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<WebElement> getAllCategories() {
        return driver.findElements(allCategories);
    }
    
    public void clickSubCategory(String mainCategory, String subCategoryName) {
        By subCat = By.xpath(String.format(subCategoryXpath, mainCategory, subCategoryName));
        wait.until(ExpectedConditions.elementToBeClickable(subCat)).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getSectionHeading() {
        try {
            WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='features_items']/h2 | //h2[contains(@class,'title text-center')]")
            ));
            return heading.getText().trim();
        } catch (TimeoutException e) {
            return "Heading not found";
        }
    }

    public void goBackHome() {
        driver.navigate().back();
        wait.until(ExpectedConditions.urlContains("automationexercise.com"));
    }

}
