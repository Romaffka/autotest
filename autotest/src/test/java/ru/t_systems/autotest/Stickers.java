package ru.t_systems.autotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Stickers {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost:9080/litecart/");
    }

    @Test
    public void stikersTest() {
        List<WebElement> products = driver.findElements(By.className("product"));
        assertFalse("no products", products.isEmpty());
        for (WebElement el :  products) {
            List<WebElement> stickers = el.findElements(By.className("sticker"));
            assertEquals( "count of stickers != 1", 1, stickers.size());
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
