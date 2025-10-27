package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    public static ExtentReports createInstance(String filePath) {
        ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
        reporter.config().setReportName("Automation Exercise Test Report");
        reporter.config().setDocumentTitle("Automation Test Results");

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "QA Automation");
        extent.setSystemInfo("Browser", "Chrome (macOS)");
        return extent;
    }
}
