package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FeatureSectionPage;

public class FeatureSectionTests extends BaseTest {
    private FeatureSectionPage featurePage;

    @Test(priority = 1)
    public void verifyHoverEffectAndColors() {
        featurePage = new FeatureSectionPage(driver);

        logStep("Scrolling to Features section");
        featurePage.scrollToFeatureSection();

        logStep("Hovering over first product card");
        featurePage.hoverOverFirstProduct();

        String buttonColor = featurePage.getAddToCartButtonColor();
        String textColor = featurePage.getAddToCartTextColor();
        String backgroundColor = featurePage.getProductCardBackground();

        logStep("Add to Cart button color: " + buttonColor);
        logStep("Add to Cart text color: " + textColor);
        logStep("Product card background: " + backgroundColor);

        Assert.assertTrue(buttonColor.contains("rgba") || buttonColor.contains("#"), "Button color not valid");
        Assert.assertTrue(textColor.contains("rgba") || textColor.contains("#"), "Text color not valid");
        Assert.assertTrue(backgroundColor.contains("rgba") || backgroundColor.contains("#"), "Background color not valid");

        logStep("Verified hover effect and colors successfully");
    }

    @Test(priority = 2)
    public void verifyAddToCartPopupAppearance() {
        featurePage.scrollToFeatureSection();
        featurePage.closeAdIfVisible();

        logStep("Hovering over product and clicking Add to Cart");
        featurePage.hoverOverFirstProduct();
        featurePage.clickAddToCart();

        logStep("Waiting for popup modal to appear");
        Assert.assertTrue(featurePage.isPopupVisible(), "Popup modal not visible!");
        Assert.assertTrue(featurePage.isPopupMessageDisplayed(), "Popup message missing!");
        logStep("Verified popup modal appears correctly with success message");
    }


    @Test(priority = 3)
    public void verifyPopupButtons() {
        logStep("Checking visibility of 'View Cart' and 'Continue Shopping' buttons");
        Assert.assertTrue(featurePage.isViewCartVisible(), "'View Cart' link not visible!");
        Assert.assertTrue(featurePage.isContinueShoppingVisible(), "'Continue Shopping' button not visible!");
        logStep("Verified popup buttons are visible");

        logStep("Clicking 'Continue Shopping' to close popup");
        featurePage.clickContinueShopping();
        logStep("Popup closed and user returned to page successfully");
    }
}
