package com.contrastsecurity.webgoat.selenium;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxScript {
    public static void run(String un, String em, String pw, String url, boolean headless, String gecko, String browserBin) {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        if (!browserBin.equals("null")) {
            File bin = new File(browserBin);
            firefoxBinary = new FirefoxBinary(bin);
        }
        if (headless) {
            firefoxBinary.addCommandLineOptions("--headless");
        }
        System.setProperty("webdriver.gecko.driver", gecko);
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        FirefoxDriver driver = new FirefoxDriver(firefoxOptions);

        try {
            driver.get(url + "/login");
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            // Login
            driver.findElement(By.name("username")).sendKeys(un);
            driver.findElement(By.name("password")).sendKeys(pw);
            driver.findElement(By.name("login-test")).click();
            driver.findElement(By.className("btn btn-custom-LOGIN btn-lg  btn-block")).click();

            // Check if user exists.  If not, create user.
            if (driver.getCurrentUrl().equals(url + "/login?error")) {
                driver.get(url + "/registration");
                driver.findElement(By.id("username")).sendKeys(un);
                driver.findElement(By.id("userEmail")).sendKeys(em);
                driver.findElement(By.id("password")).sendKeys(pw);
                driver.findElement(By.id("passwordConfirm")).sendKeys(pw);
                driver.findElement(By.name("register-test")).click();
                driver.findElement(By.className("btn btn-custom btn-lg  btn-block")).click();
            }
        } finally {
            driver.quit();
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
