package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.FeatureSectionPage;

public class CartTests extends BaseTest {
    private FeatureSectionPage featurePage;
    private CartPage cartPage;

    @Test(priority = 1)
    public void verifyProductAddedToCartAndDataCorrect() {
        featurePage = new FeatureSectionPage(driver);
        cartPage = new CartPage(driver);

        logStep("Scrolling to Features section");
        featurePage.scrollToFeatureSection();
        featurePage.closeAdIfVisible();

        logStep("Hovering over first product and clicking Add to Cart");
        featurePage.hoverOverFirstProduct();
        featurePage.clickAddToCart();

        logStep("Waiting for popup modal");
        Assert.assertTrue(featurePage.isPopupVisible(), "Popup modal not visible!");

        logStep("Clicking 'View Cart' to open cart page");
        featurePage.clickViewCart();

        logStep("Verifying cart page and product details");
        Assert.assertTrue(cartPage.isCartTableVisible(), "Cart table not visible!");
        Assert.assertTrue(cartPage.isProductInCart("Blue Top"), "Product 'Blue Top' not found in cart!");
        
        String price = cartPage.getProductPrice("Blue Top");
        String quantity = cartPage.getProductQuantity("Blue Top");
        String total = cartPage.getProductTotal("Blue Top");

        logStep("Product Price: " + price);
        logStep("Product Quantity: " + quantity);
        logStep("Product Total: " + total);

        Assert.assertTrue(price.contains("Rs. 500"), "Price mismatch!");
        Assert.assertEquals(quantity, "1", "Quantity mismatch!");
        Assert.assertTrue(total.contains("Rs. 500"), "Total mismatch!");

        logStep("Verified product data is correct in cart!");
    }
}
