package com.example.practice_sel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

public class LocalLogintest {
    private WebDriver driver;
    private String filePath;

    @BeforeMethod
    public void setUp() {
        // Configure Headless Chrome Options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Get absolute file path to the local login.html page
        File loginFile = new File("webpage/login.html");
        filePath = loginFile.toURI().toString();
    }

    @Test
    public void testValidLogin() {
        driver.get(filePath);

        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("submitBtn")).click();

        String message = driver.findElement(By.id("message")).getText();
        Assert.assertEquals(message, "Login Successful!", "Valid login test failed.");
    }

    @Test
    public void testInvalidLogin() {
        driver.get(filePath);

        driver.findElement(By.id("username")).sendKeys("wrongUser");
        driver.findElement(By.id("password")).sendKeys("wrongPassword");
        driver.findElement(By.id("submitBtn")).click();

        String message = driver.findElement(By.id("message")).getText();
        Assert.assertEquals(message, "Invalid username or password.", "Invalid login test failed.");
    }

    @Test
    public void testBackgroundColor() {
        driver.get(filePath);

        WebElement bodyElement = driver.findElement(By.tagName("body"));
        String rgbaColor = bodyElement.getCssValue("background-color");
        String hexColor = Color.fromString(rgbaColor).asHex();

        Assert.assertEquals(hexColor.toLowerCase(), "#f0f2f5", "Background color validation failed.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}   
