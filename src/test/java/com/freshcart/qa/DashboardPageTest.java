package com.freshcart.qa;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

public class DashboardPageTest {

    private WebDriver driver;
    private Eyes eyes;
    private VisualGridRunner runner;
    private static final BatchInfo BATCH = new BatchInfo("FreshCart Sprint 1 Regression");

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);

        runner = new VisualGridRunner(5);
        eyes = new Eyes(runner);

        Configuration config = eyes.getConfiguration();
        config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
        config.setBatch(BATCH);
        config.addBrowser(1200, 800, BrowserType.CHROME);
        eyes.setConfiguration(config);
    }

    @Test
    public void testDashboardWithIgnoreRegion() {
        eyes.open(driver, "FreshCart", "Dashboard - Ignore Region", new RectangleSize(1200, 800));
        driver.get("https://deepak-gurejani.github.io/Eyes-testng-lab/dashboard.html");

        eyes.check("Dashboard with Ignored Timestamp",
                Target.window().fully()
                        .ignore(driver.findElement(By.id("liveTimestamp"))));

        eyes.closeAsync();
    }

    @Test
    public void testDashboardWithFloatingRegion() {
        eyes.open(driver, "FreshCart", "Dashboard - Floating Region", new RectangleSize(1200, 800));
        driver.get("https://deepak-gurejani.github.io/Eyes-testng-lab/dashboard.html");

        eyes.check("Dashboard with Floating Timestamp",
                Target.window().fully()
                        .floating(driver.findElement(By.id("liveTimestamp")), 10, 10, 10, 10));

        eyes.closeAsync();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
        if (eyes != null) eyes.abortIfNotClosed();
        if (runner != null) {
            TestResultsSummary allTestResults = runner.getAllTestResults(false);
            System.out.println("Dashboard UFG Results: " + allTestResults);
        }
    }
}