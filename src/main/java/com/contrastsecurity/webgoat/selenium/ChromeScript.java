package com.contrastsecurity.webgoat.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeScript {
    public static void run(String un, String em, String pw, String url, String driverPath) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(url);
            driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

            // Login
            driver.findElement(By.xpath("//input[@name='username']")).sendKeys(un);
            driver.findElement(By.xpath("//input[@name='password']")).sendKeys(pw);
            driver.findElement(By.xpath("//button[@name='login-test']")).click();
            // Check if user exists.  If not, create user.
            if (driver.getCurrentUrl().equals(url + "?error")) {
                driver.get(url.substring(0,url.lastIndexOf("/"))+ "/registration");
                driver.findElement(By.xpath("//input[@id='username']")).sendKeys(un);
                driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys(em);
                driver.findElement(By.xpath("//input[@id='password']")).sendKeys(pw);
                driver.findElement(By.xpath("//input[@id='passwordConfirm']")).sendKeys(pw);
                driver.findElement(By.xpath("//button[@id='register']")).click();
            }
            System.out.println("Successfully finished Chrome script!");
        } finally {
            Thread.sleep(10000);
            driver.close();
        }
    }
}
