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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CountriesAndZonesTest {

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
        WebElement element = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title= 'Logout']")));
    }

    @Test
    public void countriesTest() {
        driver.get("http://localhost:9080/litecart/admin/?app=countries&doc=countries");
        checkSortedItems("tr.row","./td[5]");
    }

    @Test
    public void countriesAndZoneTest() {
        driver.get("http://localhost:9080/litecart/admin/?app=countries&doc=countries");

        List<WebElement> rows = driver.findElements(By.cssSelector(".dataTable tr.row"));
        assertEquals("", false, rows.size() == 0);

        for(int i = 0; i < rows.size(); i++){
            String zone = rows.get(i).findElement(By.xpath("./td[6]")).getText();
            int numberZone = Integer.parseInt(zone);
            if (numberZone == 0) {
                continue;
            }
            WebElement country = rows.get(i).findElement(By.xpath("./td[5]/a"));
            country.click();
            checkSortedItems("tr:not(.header)", "./td[3]");
            driver.navigate().back();
            rows = driver.findElements(By.cssSelector(".dataTable tr.row"));
        }
    }

    @Test
    public void ZoneTest() {
        driver.get("http://localhost:9080/litecart/admin/?app=geo_zones&doc=geo_zones");
        List<WebElement> rows = driver.findElements(By.cssSelector(".dataTable tr.row"));
        assertEquals("", false, rows.size() == 0);

        for(int i = 0; i < rows.size(); i++){
            WebElement country = rows.get(i).findElement(By.xpath("./td[3]/a"));
            country.click();
            checkSortedItems("select[name*='zone_code']", "./option[@selected]");
            driver.navigate().back();
            rows = driver.findElements(By.cssSelector(".dataTable tr.row"));
        }
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    private void checkSortedItems(String rowSelector, String itemXPath) {
        List<String> items = getTableItemList(rowSelector, itemXPath);
        assertEquals("", false, items.size() == 0);
        List<String> sortedItems = new ArrayList<>(items);
        sortedItems.sort(Comparator.comparing(String::toString));
        assertTrue(items.equals(sortedItems));
    }

    private List<String> getTableItemList(String rowSelector, String itemXPath) {
        List<WebElement> rows = driver.findElements(By.cssSelector(".dataTable " + rowSelector));
        List<String> items = new ArrayList<>();
        for(WebElement el : rows){
            String item = el.findElement(By.xpath(itemXPath)).getText();
            if (!item.isEmpty()) {
                items.add(item);
            }
        }
        return items;
    }
}
