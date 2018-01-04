package ru.t_systems.autotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OpenNewWindow {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".fa.fa-external-link")
    private List<WebElement> externalLink;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost:9080/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title= 'Logout']")));
        PageFactory.initElements(driver, this);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void openNewWindow() {
        driver.get("http://localhost:9080/litecart/admin/?app=countries&doc=countries");
        List<WebElement> rows = driver.findElements(By.cssSelector(".dataTable tr.row"));
        assertEquals("", false, rows.size() == 0);

        WebElement country = rows.get(0).findElement(By.xpath("./td[5]/a"));
        country.click();

        String mainWindow = driver.getWindowHandle();

        for (WebElement el : externalLink) {
            el.click();
            String newWindow = wait.until((d) -> getNewWindowHandle(mainWindow));
            driver.switchTo().window(newWindow);
            List<WebElement> anyDivs = driver.findElements(By.cssSelector("div"));
            assertFalse(anyDivs.isEmpty());
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    private String getNewWindowHandle(String exceptMainWindow) {
        Set<String> handles = driver.getWindowHandles();
        if (handles.size() == 1) {
            return null;
        }
        assertTrue(handles.size() == 2);
        handles.remove(exceptMainWindow);
        return handles.iterator().next();
    }
}
