package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BrandsPage;

import java.util.Map;

public class BrandsTests extends BaseTest {
    private BrandsPage brandsPage;

    @Test(priority = 1)
    public void verifyBrandsListNamesAndCounts() {
        brandsPage = new BrandsPage(driver);

        logStep("Scrolling to Brands section");
        brandsPage.scrollToBrandsSection();

        logStep("Getting all brands and their counts");
        Map<String, Integer> brandMap = brandsPage.getBrandsList();

        logStep("Found " + brandMap.size() + " brands on the page");
        for (Map.Entry<String, Integer> entry : brandMap.entrySet()) {
            logStep(entry.getKey() + " → " + entry.getValue());
        }

        Map<String, Integer> expectedBrands = Map.of(
                "POLO", 6,
                "H&M", 5,
                "MADAME", 5,
                "MAST & HARBOUR", 3,
                "BABYHUG", 4,
                "ALLEN SOLLY JUNIOR", 3,
                "KOOKIE KIDS", 3,
                "BIBA", 5
        );

        for (String brand : expectedBrands.keySet()) {
            Assert.assertTrue(brandMap.containsKey(brand), "Brand missing: " + brand);
            Assert.assertEquals(brandMap.get(brand), expectedBrands.get(brand),
                    "Count mismatch for " + brand);
        }

        logStep("Verified all brand names and counts correctly");
    }

    @Test(priority = 2)
    public void verifyBrandClickNavigation() {
        String brandToTest = "POLO";
        logStep("Clicking on brand: " + brandToTest);
        brandsPage.clickBrand(brandToTest);

        logStep("Verifying redirected page heading reflects brand name");
        Assert.assertTrue(brandsPage.isBrandPageHeadingCorrect(brandToTest),
                "Brand page heading does not match brand name!");

        logStep("Verified brand click navigates to correct brand page");
    }
    
    @Test(priority = 3)
    public void verifyBreadcrumbReflectsCorrectBrandNavigation() {
        String brandToTest = "Polo";
        logStep("Clicking on brand: " + brandToTest);
        brandsPage.clickBrand(brandToTest);

        logStep("Verifying breadcrumb is visible");
        Assert.assertTrue(brandsPage.isBreadcrumbVisible(), "Breadcrumb is not visible on brand page!");

        String breadcrumbText = brandsPage.getBreadcrumbText();
        logStep("Captured breadcrumb: " + breadcrumbText);

        logStep("Verifying breadcrumb reflects correct navigation for brand: " + brandToTest);
        Assert.assertTrue(brandsPage.isBreadcrumbCorrect(brandToTest),
                "Breadcrumb does not contain correct brand/category path!");

        logStep("Verified breadcrumb displays correct navigation: " + breadcrumbText);
    }

}
