package base;

import com.aventstack.extentreports.*;

import pages.HomePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentManager;
import utils.TestUtils;

import java.awt.Desktop;
import java.io.File;
import java.lang.reflect.Method;

public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl = "https://automationexercise.com/";
    protected static ExtentReports extent;
    protected ExtentTest test;
    
    private HomePage homePage;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentManager.createInstance("test-output/ExtentReport.html");
    }

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.get(baseUrl);
        
    }

    @BeforeMethod
    public void startTest(Method method) {
        test = extent.createTest(method.getName());
        homePage = new HomePage(driver);
        homePage.removeGoogleAds();  
    }

    protected void logStep(String message) {
        String screenshotPath = TestUtils.takeScreenshot(driver, message);
        test.log(Status.INFO, message);
        try {
            test.addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            test.log(Status.WARNING, "Could not attach screenshot: " + e.getMessage());
        }
    }

    @AfterMethod
    public void captureResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = TestUtils.takeScreenshot(driver, result.getName());
            test.log(Status.FAIL, result.getThrowable());
            test.addScreenCaptureFromPath(screenshotPath, "Screenshot on Failure");
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test passed successfully");
        } else {
            test.log(Status.SKIP, "Test skipped");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void closeReport() {
        extent.flush();
        try {
            Desktop.getDesktop().browse(new File("test-output/ExtentReport.html").toURI());
        } catch (Exception e) {
            System.out.println("Unable to open report automatically: " + e.getMessage());
        }
    }
}
