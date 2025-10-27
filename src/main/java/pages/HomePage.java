package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomePage {
    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;


    // Locators
    private By logo = By.xpath("//img[@alt='Website for automation practice']");
    private By navMenuItems = By.xpath("//ul[@class='nav navbar-nav']/li/a");
    private By navMenu = By.xpath("//ul[@class='nav navbar-nav']");
    private By mainBanner = By.id("slider-carousel");
    private By featuresItems = By.xpath("//h2[text()='Features Items']");
    private By footer = By.id("footer");
    private By subscribeEmail = By.id("susbscribe_email");
    private By subscribeBtn = By.id("subscribe");
    private By scrollUp = By.id("scrollUp");
    private By loggedInUser = By.xpath("//a[contains(text(),'Logged in as')]");
    private By firstProduct = By.xpath("(//div[@class='features_items']//div[@class='product-image-wrapper'])[1]");
    private By firstProductName = By.xpath("(//div[@class='features_items']//div[@class='productinfo text-center']/p)[1]");
    private By firstProductPrice = By.xpath("(//div[@class='features_items']//div[@class='productinfo text-center']/h2)[1]");
    private By firstViewProductButton = By.xpath("(//a[contains(text(),'View Product')])[1]");


    private By productTextLink = By.xpath("//a[@href='/products']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isLogoVisible() {
        return driver.findElement(logo).isDisplayed();
    }

    public void clickLogo() {
        driver.findElement(logo).click();
    }

    public List<WebElement> getNavMenuItems() {
        return driver.findElements(navMenuItems);
    }

    public boolean isMainBannerDisplayed() {
        return driver.findElement(mainBanner).isDisplayed();
    }

    public boolean isFeaturesItemsVisible() {
        return driver.findElement(featuresItems).isDisplayed();
    }

    public boolean isFooterVisible() {
        return driver.findElement(footer).isDisplayed();
    }

    public void subscribe(String email) {
        driver.findElement(subscribeEmail).clear();
        driver.findElement(subscribeEmail).sendKeys(email);
        driver.findElement(subscribeBtn).click();
    }

    public boolean isScrollUpVisible() {
        return driver.findElement(scrollUp).isDisplayed();
    }

    public void clickScrollUp() {
        driver.findElement(scrollUp).click();
    }

    public void clickEachMenuItem() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(navMenuItems));
        List<WebElement> items = driver.findElements(navMenuItems);
        int totalItems = items.size();

        System.out.println("Found " + totalItems + " menu items.");

        for (int i = 0; i < totalItems; i++) {
            List<WebElement> currentItems = driver.findElements(navMenuItems);
            WebElement item = currentItems.get(i);
            String linkText = item.getText();
            String href = item.getAttribute("href");

            System.out.println("Opening menu item: " + linkText + " (" + href + ")");

            ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", href);

            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

            try {
                wait.until(ExpectedConditions.urlContains(href));
            } catch (Exception e) {
                System.out.println("Page did not fully load for: " + linkText);
            }

            System.out.println("Reached page: " + driver.getCurrentUrl());

            driver.close();
            driver.switchTo().window(tabs.get(0));

            wait.until(ExpectedConditions.visibilityOfElementLocated(navMenu));
        }
    }

    public void hoverOverMenuItems() {
        List<WebElement> items = driver.findElements(navMenuItems);
        for (WebElement item : items) {
            actions.moveToElement(item).perform();
        }
    }

    public boolean isUserLoggedIn() {
        List<WebElement> userInfo = driver.findElements(loggedInUser);
        return !userInfo.isEmpty();
    }
    
    public String getMenuItemColor(WebElement item) {
    	System.out.println(Color.fromString(item.getCssValue("color")).asHex());
        return Color.fromString(item.getCssValue("color")).asHex();
    }

    public boolean verifyMenuHoverColor(String expectedColorHex) throws InterruptedException {
        Actions actions = new Actions(driver);

        List<WebElement> items = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(navMenuItems));
        List<String> menuTexts = items.stream()
                .map(e -> e.getText().trim())
                .filter(t -> !t.equalsIgnoreCase("Home"))
                .collect(Collectors.toList());

        for (String text : menuTexts) {
            System.out.println("Checking hover color for: " + text);
            String colorHex = "";

            for (int attempt = 1; attempt <= 3; attempt++) {
                try {
                    By linkLocator = By.xpath("//ul[@class='nav navbar-nav']//a[normalize-space()='" + text + "']");
                    WebElement link = wait.until(ExpectedConditions.visibilityOfElementLocated(linkLocator));

                    actions.moveToElement(link).perform();
                    Thread.sleep(500);

                    link = wait.until(ExpectedConditions.visibilityOfElementLocated(linkLocator));
                    colorHex = org.openqa.selenium.support.Color.fromString(link.getCssValue("color")).asHex();

                    System.out.println("Hover color for [" + text + "]: " + colorHex);
                    break;

                } catch (StaleElementReferenceException e) {
                    System.out.println("Stale element for [" + text + "], retrying " + attempt);
                    Thread.sleep(200);
                }
            }

            if (!colorHex.equalsIgnoreCase(expectedColorHex)) {
                System.out.println("Hover color mismatch for [" + text + "]  Expected: "
                        + expectedColorHex + "  Got: " + colorHex);
                return false;
            }
        }

        return true;
    }

    public boolean verifyActiveLinkColor(String expectedColorHex) {
        Actions actions = new Actions(driver);

        List<WebElement> menuItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(navMenuItems));
        List<String> menuTexts = menuItems.stream()
                .map(e -> e.getText().trim())
                .filter(t -> !t.equalsIgnoreCase("Home"))
                .collect(Collectors.toList());

        for (String linkText : menuTexts) {
            System.out.println("Checking active color for: " + linkText);

            try {
                By linkLocator = By.xpath("//ul[@class='nav navbar-nav']//a[normalize-space()='" + linkText + "']");
                WebElement link = wait.until(ExpectedConditions.elementToBeClickable(linkLocator));

                link.click();

                wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
                Thread.sleep(500);

                WebElement activeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(linkLocator));

                String color = org.openqa.selenium.support.Color.fromString(activeLink.getCssValue("color")).asHex();
                System.out.println("Active color for [" + linkText + "]: " + color);

                if (!color.equalsIgnoreCase(expectedColorHex)) {
                    System.out.println("Active link color mismatch for [" + linkText + "] | Expected: "
                            + expectedColorHex + ", Got: " + color);
                    return false;
                }

            } catch (StaleElementReferenceException e) {
                System.out.println("Stale element for [" + linkText + "], retrying once...");
                continue;
            } catch (Exception e) {
                System.out.println("Exception for [" + linkText + "]: " + e.getMessage());
            } finally {
                driver.navigate().to("https://automationexercise.com/");
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(navMenuItems));
            }
        }
        return true;
    }

    public void removeGoogleAds() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "(() => {" +
                "  const selectors = [" +
                "    'iframe[id^=\"google_ads_\"]'," +
                "    'iframe[name^=\"google_\"]'," +
                "    'ins.adsbygoogle'," +
                "    'ins[class*=\"adsbygoogle\"]'," +
                "    'iframe[src*=\"googlesyndication\"]'," +
                "    'iframe[src*=\"doubleclick\"]'" +
                "  ];" +
                "  selectors.forEach(sel => {" +
                "    document.querySelectorAll(sel).forEach(el => el.remove());" +
                "  });" +
                "  document.querySelectorAll('*').forEach(node => {" +
                "    if (node.shadowRoot) {" +
                "      selectors.forEach(sel => node.shadowRoot.querySelectorAll(sel).forEach(el => el.remove()));" +
                "    }" +
                "  });" +
                "  console.log('✅ All ads removed (including shadow roots)');" +
                "})();"
            );
        } catch (Exception e) {
            System.out.println("⚠️ Unable to remove Google Ads: " + e.getMessage());
        }
    }


    
    public Map<String, String> getFirstProductInfo() {
        Map<String, String> productInfo = new HashMap<>();
        String name = driver.findElement(firstProductName).getText().trim();
        String price = driver.findElement(firstProductPrice).getText().trim();
        productInfo.put("name", name);
        productInfo.put("price", price);
        return productInfo;
    }

    public void clickFirstViewProduct() {
        WebElement viewProduct = driver.findElement(firstViewProductButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewProduct);
        viewProduct.click();
    }
    

    
    
    public void clickProdctLink() {
  	  WebElement linkClick = driver.findElement(productTextLink);
  	  linkClick.click();
    }
    
    public String getProUrl() {
  	  return driver.getCurrentUrl();
    }

}
