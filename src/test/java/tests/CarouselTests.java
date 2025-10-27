package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CarouselPage;

public class CarouselTests extends BaseTest {
    private CarouselPage carouselPage;

    @Test(priority = 0)
    public void verifyCarouselAutoRotation() {
        carouselPage = new CarouselPage(driver);
        logStep("Waiting for carousel to auto-rotate after page load");

        int initialSlide = carouselPage.getActiveSlideIndex();
        logStep("Initial active slide index: " + initialSlide);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int newSlide = carouselPage.getActiveSlideIndex();
        logStep("Slide index after 5 seconds: " + newSlide);

        Assert.assertNotEquals(newSlide, initialSlide, "Carousel did not auto-rotate on its own!");
        logStep("Verified carousel automatically rotates after page load");
    }


    @Test(priority = 1)
    public void verifyCarouselIsVisible() {
        carouselPage = new CarouselPage(driver);
        logStep("Verifying carousel visibility on homepage");
        Assert.assertTrue(carouselPage.isCarouselVisible(), "Carousel is not visible!");
        logStep("Carousel section is visible");
    }

    @Test(priority = 2)
    public void verifyArrowsExist() {
        logStep("Checking visibility of left and right arrows");
        Assert.assertTrue(carouselPage.isLeftArrowVisible(), "Left arrow not visible!");
        Assert.assertTrue(carouselPage.isRightArrowVisible(), "Right arrow not visible!");
        logStep("Verified both arrows are visible");
    }

    @Test(priority = 3)
    public void verifyRightArrowFunctionality() {
        logStep("Getting current active slide index");
        int oldIndex = carouselPage.getActiveSlideIndex();

        logStep("Clicking right arrow to move to next slide");
        carouselPage.clickRightArrow();

        logStep("Waiting for slide change");
        Assert.assertTrue(carouselPage.waitForSlideChange(oldIndex),
                "Right arrow did not change the slide!");
        logStep("Verified right arrow changes slide successfully");
    }

    @Test(priority = 4)
    public void verifyLeftArrowFunctionality() {
        logStep("Getting current active slide index");
        int oldIndex = carouselPage.getActiveSlideIndex();

        logStep("Clicking left arrow to move to previous slide");
        carouselPage.clickLeftArrow();

        logStep("Waiting for slide change");
        Assert.assertTrue(carouselPage.waitForSlideChange(oldIndex),
                "Left arrow did not change the slide!");
        logStep("Verified left arrow changes slide successfully");
    }

    @Test(priority = 5)
    public void verifyIndicatorChangeOnSlideMove() {
        logStep("Getting current active indicator index");
        int oldIndicator = carouselPage.getActiveIndicatorIndex();

        logStep("Clicking right arrow to move carousel");
        carouselPage.clickRightArrow();

        logStep("Waiting for indicator to update");
        Assert.assertTrue(carouselPage.waitForIndicatorChange(oldIndicator),
                "Dot indicator did not change with slide!");
        logStep("Verified indicator updates correctly when slide changes");
    }
    
  
}
