package ru.t_systems.autotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class Adminka {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        File file= new File("C:\\Program Files\\Nightly\\firefox.exe");
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost:9080/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title= 'Logout']")));
        assertEquals("URL is not correct",
                "http://localhost:9080/litecart/admin/", driver.getCurrentUrl());
    }

    @Test
    public void headersTest() {
       List<WebElement> mainHeaders = driver.findElements(By.id("app-"));
       for (int i = 0; i < mainHeaders.size(); i++) {
           mainHeaders.get(i).click();
           List<WebElement> childHeaders = driver.findElements(By.xpath("//ul[@class='docs']//span[@class= 'name']"));
           showMenuTitle();
           for (int j = 0; j < childHeaders.size(); j++) {
               childHeaders.get(j).click();
               showMenuTitle();
               childHeaders = driver.findElements(By.xpath("//ul[@class='docs']//span[@class= 'name']"));
           }
           mainHeaders = driver.findElements(By.id("app-"));
       }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private void showMenuTitle() {
        WebElement h1 = driver.findElement(By.tagName("h1"));
        assertTrue(h1.isDisplayed());
    }
}
