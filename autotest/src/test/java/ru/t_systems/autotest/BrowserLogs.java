package ru.t_systems.autotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class BrowserLogs {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost:9080/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title= 'Logout']")));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void browserLogsIsEmpty() {
        driver.get("http://localhost:9080/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        List<WebElement> rows = driver.findElements(By.xpath("//*[@class='dataTable']//tr[@class='row']/td[3]/a"));
        for (int i = 0; i < rows.size(); i++) {
            String href = rows.get(i).getAttribute("href");
            if (!href.contains("category_id=1")) {
                continue;
            }
            rows.get(i).click();
            driver.navigate().back();
            rows = driver.findElements(By.xpath("//*[@class='dataTable']//tr[@class='row']/td[3]/a"));
        }

        List<LogEntry> logs = driver.manage().logs().get("browser").getAll();
        assertTrue(logs.isEmpty());
    }
}
