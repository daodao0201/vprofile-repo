package com.contrastsecurity.webgoat.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxScript {
    public static void run(String un, String em, String pw, String url, String gecko) throws InterruptedException {

        System.setProperty("webdriver.gecko.driver", gecko);
        FirefoxDriver driver = new FirefoxDriver();

        try {
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

            // Login
            driver.findElement(By.xpath("//input[@name='username']")).sendKeys(un);
            driver.findElement(By.xpath("//input[@name='password']")).sendKeys(pw);
            driver.findElement(By.xpath("//button[@name='login-test']")).click();
            // url= driver.getCurrentUrl();
            // Check if user exists.  If not, create user.
            if (driver.getCurrentUrl().equals(url + "?error")) {
                driver.get(url.substring(0,url.lastIndexOf("/"))+ "/registration");
                driver.findElement(By.xpath("//input[@id='username']")).sendKeys(un);
                driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys(em);
                driver.findElement(By.xpath("//input[@id='password']")).sendKeys(pw);
                driver.findElement(By.xpath("//input[@id='passwordConfirm']")).sendKeys(pw);
                driver.findElement(By.xpath("//button[@id='register']")).click();
            }
        } finally {
            Thread.sleep(10000);
            driver.close();
        }
    }
}
