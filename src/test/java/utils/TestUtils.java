package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {

    public static String takeScreenshot(WebDriver driver, String stepName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String safeStep = stepName.replaceAll("[^a-zA-Z0-9]", "_");
        String screenshotName = safeStep + "_" + timestamp + ".png";
        String screenshotPath = System.getProperty("user.dir") + "/test-output/screenshots/" + screenshotName;

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(screenshotPath);
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            System.out.println("❌ Failed to capture screenshot: " + e.getMessage());
        }

        return screenshotPath;
    }
}
