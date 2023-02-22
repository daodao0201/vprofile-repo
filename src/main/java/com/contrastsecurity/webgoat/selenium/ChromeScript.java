package com.contrastsecurity.webgoat.selenium;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeScript {
    public static void run(String un, String em, String pw, String url, boolean headless, String driverPath, String browserBin) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (!browserBin.equals("null")) {
            chromeOptions.addExtensions(new File(browserBin));
        }

        chromeOptions.addArguments("--verbose");

        if (headless) {
            chromeOptions.addArguments("--headless");
        }
        if (System.getProperty("os.name").startsWith("Windows")) {
            chromeOptions.addArguments("--disable-gpu");
        }
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver(chromeOptions);

        try {
            driver.get(url + "/login");
            driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

            // Login
            driver.findElement(By.name("username")).sendKeys(un);
            driver.findElement(By.name("password")).sendKeys(pw);
            driver.findElement(By.name("login-test")).click();
            //driver.findElement(By.className("btn btn-custom-LOGIN btn-lg  btn-block")).click();

            // Check if user exists.  If not, create user.
            if (driver.getCurrentUrl().equals(url + "/login?error")) {
                driver.get(url + "/registration");
                driver.findElement(By.id("username")).sendKeys(un);
                driver.findElement(By.id("userEmail")).sendKeys(em);
                driver.findElement(By.id("password")).sendKeys(pw);
                driver.findElement(By.id("matchingPassword")).sendKeys(pw);
                driver.findElement(By.name("register-test")).click();
                //driver.findElement(By.className("btn btn-custom btn-lg  btn-block")).click();
            }
            System.out.println("Successfully finished Chrome script!");
        } finally {
            driver.quit();
        }
    }

    private static void retryingFindSendKeys(WebDriver driver, By by, String text) {
        //boolean result = false;
        int attempts = 0;
        while(attempts < 100) {
            try {
                driver.findElement(by).sendKeys(text);
                //result = true;
                break;
            } catch(StaleElementReferenceException e) {}
            attempts++;
        }
    }

    private static void delay (long time) {
        try {
            Thread.sleep(time);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
