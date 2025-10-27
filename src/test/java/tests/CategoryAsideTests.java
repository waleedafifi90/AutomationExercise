package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CategoryPage;

public class CategoryAsideTests extends BaseTest {
    private CategoryPage categoryPage;

    @Test(priority = 1)
    public void verifyCategorySectionVisible() {
        categoryPage = new CategoryPage(driver);
        logStep("Scrolling to and checking if Category sidebar is visible on homepage");
        categoryPage.scrollToCategorySection();
        Assert.assertTrue(categoryPage.isCategorySectionVisible(), "Category section not visible!");
        logStep("Verified Category section is visible");
    }

    @Test(priority = 2)
    public void verifyCategoryExpansion() {
        String category = "Women";
        logStep("Scrolling to Category section before expanding");
        categoryPage.scrollToCategorySection();

        logStep("Clicking category to expand: " + category);
        categoryPage.clickCategory(category);

        Assert.assertTrue(categoryPage.isCategoryExpanded(category),
                category + " category did not expand (still has 'collapse' class)!");
        logStep("Verified category expanded successfully: " + category);
    }

    @Test(priority = 3)
    public void verifyCategoryCollapse() {
        String category = "Women";
        logStep("Scrolling to Category section before collapsing");
        categoryPage.scrollToCategorySection();

        logStep("Clicking category again to collapse: " + category);
        categoryPage.clickCategory(category);

        Assert.assertTrue(categoryPage.isCategoryCollapsed(category),
                category + " category did not collapse (collapse class missing)!");
        logStep("Verified category collapsed successfully: " + category);
    }

    @Test(priority = 4)
    public void verifySingleCategoryExpansion() throws InterruptedException {
        String firstCategory = "Women";
        String secondCategory = "Men";

        logStep("Scrolling to Category section before testing multiple expansion behavior");
        categoryPage.scrollToCategorySection();

        logStep("Expanding first category: " + firstCategory);
        categoryPage.clickCategory(firstCategory);
        Assert.assertTrue(categoryPage.isCategoryExpanded(firstCategory), firstCategory + " should be expanded");
        
        Thread.sleep(1000);
        
        logStep("Clicking another category: " + secondCategory);
        categoryPage.clickCategory(secondCategory);

        Assert.assertTrue(categoryPage.isCategoryExpanded(secondCategory), secondCategory + " should be expanded");
        Assert.assertTrue(categoryPage.isCategoryCollapsed(firstCategory),
                firstCategory + " should collapse when " + secondCategory + " expands");

        logStep("Verified only one category remains expanded at a time");
    }

    @Test(priority = 5)
    public void verifySubCategoryVisibility() {
        String mainCategory = "Women";
        String subCategory = "Dress";

        logStep("Scrolling to Category section before expanding");
        categoryPage.scrollToCategorySection();

        logStep("Expanding category: " + mainCategory);
        categoryPage.clickCategory(mainCategory);

        logStep("Checking if subcategory is visible: " + subCategory);
        Assert.assertTrue(categoryPage.isSubCategoryVisible(mainCategory, subCategory),
                "Subcategory " + subCategory + " not visible under expanded category!");
        logStep("Verified subcategory visibility successfully");
    }

    @Test(priority = 6)
    public void verifySubCategoryNavigationAndPageDetails() {
        String mainCategory = "Kids";
        String subCategory = "Dress";

        categoryPage.scrollToCategorySection();
        logStep("Expanding main category: " + mainCategory);
        categoryPage.clickCategory(mainCategory);

        logStep("Clicking on subcategory: " + subCategory);
        categoryPage.clickSubCategory(mainCategory, subCategory);

        logStep("Verifying page title and section heading reflect the subcategory");
        String actualTitle = categoryPage.getPageTitle();
        String actualHeading = categoryPage.getSectionHeading();

        logStep("Page title: " + actualTitle);
        logStep("Page heading: " + actualHeading);

        Assert.assertTrue(
            actualTitle.toLowerCase().contains(subCategory.toLowerCase()) ||
            actualHeading.toLowerCase().contains(subCategory.toLowerCase()),
            "Page title or heading does not reflect the subcategory: " + subCategory
        );

        logStep("Verified subcategory navigation reflected correctly in title and heading");

        logStep("Navigating back to homepage");
        categoryPage.goBackHome();

        Assert.assertTrue(driver.getCurrentUrl().equals(baseUrl) || actualTitle.contains("Automation Exercise"),
                "Did not return to homepage properly!");
        logStep("Returned to homepage successfully");
    }

}
