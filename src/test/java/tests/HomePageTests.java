package tests;

import base.BaseTest;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductDetailsPage;

public class HomePageTests extends BaseTest {
    private HomePage homePage;


    @Test(priority = 1)
    public void verifyUrlLoadsSuccessfully() {
        homePage = new HomePage(driver);
        logStep("Navigated to Automation Exercise homepage");
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl, "URL did not load correctly!");
        logStep("Verified correct URL loaded");
    }

    @Test(priority = 2)
    public void verifyPageTitle() {
        String title = homePage.getPageTitle();
        logStep("Captured page title: " + title);
        Assert.assertTrue(title.contains("Automation Exercise"), "Title mismatch!");
        logStep("Verified page title successfully");
    }

    @Test(priority = 3)
    public void verifyCompanyLogoVisibility() {
        logStep("Checking if logo is visible");
        Assert.assertTrue(homePage.isLogoVisible(), "Logo not visible!");
        logStep("Verified company logo is visible");
    }

    @Test(priority = 4)
    public void verifyClickLogoRedirectsHome() {
        logStep("Clicking company logo to navigate home");
        homePage.clickLogo();
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl);
        logStep("Verified logo redirects to home page");
    }

    @Test(priority = 5)
    public void verifyNavigationMenuItems() {
        int count = homePage.getNavMenuItems().size();
        logStep("Navigation menu item count: " + count);
        Assert.assertTrue(count > 0, "Navigation items missing!");
        logStep("Verified navigation menu items visible");
    }

    @Test(priority = 6)
    public void verifyMainBannerDisplayed() {
        logStep("Checking main banner visibility");
        Assert.assertTrue(homePage.isMainBannerDisplayed(), "Main banner not visible!");
        logStep("Verified main banner is displayed");
    }

    @Test(priority = 7)
    public void verifyFeaturesItemsSectionDisplayed() {
        logStep("Checking FEATURES ITEMS section visibility");
        Assert.assertTrue(homePage.isFeaturesItemsVisible(), "FEATURES ITEMS not visible!");
        logStep("Verified FEATURES ITEMS section visible");
    }

    @Test(priority = 8)
    public void verifyFooterPresence() {
        logStep("Checking footer visibility");
        Assert.assertTrue(homePage.isFooterVisible(), "Footer not visible!");
        logStep("Verified footer visible");
    }

    @Test(priority = 9)
    public void verifyNewsletterSubscription() {
        logStep("Attempting newsletter subscription with valid email");
        homePage.subscribe("test@example.com");
        logStep("Submitted newsletter subscription");
    }

    @Test(priority = 10)
    public void verifyScrollUpButton() {
        logStep("Checking scroll-up button visibility");
        Assert.assertTrue(homePage.isScrollUpVisible(), "Scroll Up button not visible!");
        logStep("Clicking scroll-up button");
        homePage.clickScrollUp();
        logStep("Verified Scroll Up button functionality");
    }
    
    @Test(priority = 11)
    public void verifyEachMenuItemIsClickable() {
        logStep("Verifying each navigation menu item is clickable");
        homePage.clickEachMenuItem();
        logStep("All navigation menu items were clickable and navigable");
    }

    @Test(priority = 12)
    public void verifyHoverEffectOnNavigationLinks() {
        logStep("Hovering over each navigation menu item to verify hover effect");
        homePage.hoverOverMenuItems();
        logStep("Verified hover effect on navigation links (hover action performed)");
    }

    @Test(priority = 13)
    public void verifyUserNotLoggedInAtFirstLoad() {
        logStep("Checking if user is logged in on first page load");
        boolean loggedIn = homePage.isUserLoggedIn();
        Assert.assertFalse(loggedIn, "User appears to be logged in without authentication!");
        logStep("Verified user is NOT logged in on first page load");
    }
    
    @Test(priority = 14)
    public void verifyMenuHoverAndActiveColors() throws InterruptedException {
        logStep("Verifying hover and active link colors (excluding Home)");
        String expectedOrangeHex = "#fe980f";
        String activeColorHex = "#ffa500";

        boolean hoverColorCheck = homePage.verifyMenuHoverColor(expectedOrangeHex);
        Assert.assertTrue(hoverColorCheck, "Hover color check failed for some items");

        boolean activeColorCheck = homePage.verifyActiveLinkColor(activeColorHex);
        Assert.assertTrue(activeColorCheck, "Active link color check failed");

        logStep("Verified hover and active link colors are orange, excluding Home.");
    }

    @Test(priority = 20)
    public void verifyProductDetailsConsistency() {
        logStep("Verifying navigation from Home → Product Details page and data consistency");

        HomePage homePage = new HomePage(driver);
        Map<String, String> homeProduct = homePage.getFirstProductInfo();
        logStep("Captured product info from Home: " + homeProduct);

        homePage.clickFirstViewProduct();

        ProductDetailsPage detailsPage = new ProductDetailsPage(driver);
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-information")));

        String detailsName = detailsPage.getProductName();
        String detailsPrice = detailsPage.getProductPrice();

        logStep("Product details page info → Name: " + detailsName + ", Price: " + detailsPrice);

        Assert.assertEquals(detailsName, homeProduct.get("name"), "Product name mismatch");
        Assert.assertEquals(detailsPrice, homeProduct.get("price"), "Product price mismatch");

        Assert.assertEquals(detailsPage.getQuantityValue(), "1", "Default quantity should be 1");

        Assert.assertTrue(detailsPage.isAddToCartVisible(), "Add to Cart button not visible");

        Assert.assertTrue(detailsPage.areDetailsSectionsVisible(),
                "Availability, Condition, or Brand section missing");

        logStep("Product details validated successfully");
    }


}
