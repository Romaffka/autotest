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

import java.io.File;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class AddNewProduct {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//td[@id='content']/div/a[2]")
    private WebElement createNewProductButton;

    //general
    @FindBy(css = "[name='status'][value='1']")
    private WebElement status;

    @FindBy(css = "[name='name[en]']")
    private WebElement name;

    @FindBy(css = "[name='code']")
    private WebElement code;

    @FindBy(css = "[name='quantity']")
    private WebElement quantity;

    @FindBy(css = "[type='file']")
    private WebElement uploadFile;

    //info
    @FindBy(xpath = "//ul[@class='index']/li[2]")
    private WebElement infoTab;

    @FindBy(css = "[name='manufacturer_id']")
    private WebElement manufacturer;

    @FindBy(css = "[name='manufacturer_id'] option[value='1']")
    private WebElement firstManufacturer;

    @FindBy(css = "[name='short_description[en]']")
    private WebElement shortDescription;

    @FindBy(css = ".trumbowyg-editor")
    private WebElement description;

    //prices
    @FindBy(xpath = "//ul[@class='index']/li[4]")
    private WebElement pricesTab;

    @FindBy(css = "[name='purchase_price']")
    private WebElement purchasePrice;

    @FindBy(css = "[name='prices[USD]']")
    private WebElement usdPrice;

    @FindBy(css = "[name='save']")
    private WebElement saveButton;

    @FindBy(xpath = "//tr[@class='row']/td[3]/a")
    private List<WebElement> products;

    private final String filePath = "images/image.jpg";

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost:9080/litecart/admin");
        PageFactory.initElements(driver, this);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@title= 'Logout']")));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void addNewProduct() {

        Random rnd = new Random();
        String productName = "Duck " + rnd.nextInt();

        List<WebElement> mainHeaders = driver.findElements(By.id("app-"));
        mainHeaders.get(1).click();
        createNewProductButton.click();
        status.click();
        name.sendKeys(productName);
        code.sendKeys("1");
        quantity.sendKeys("1");

        // get absolute file path
        ClassLoader classLoader = getClass().getClassLoader();
        String path = new File(classLoader.getResource(filePath).getPath()).getAbsolutePath();
        uploadFile.sendKeys(path);

        infoTab.click();

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//select[@name='manufacturer_id']")));

        manufacturer.click();
        firstManufacturer.click();
        shortDescription.sendKeys("balablabla");
        description.sendKeys("long blablabla");

        pricesTab.click();

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='purchase_price']")));

        purchasePrice.clear();
        purchasePrice.sendKeys("10");
        usdPrice.sendKeys("50");

        saveButton.click();

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='dataTable']")));

        // check product was created
        int foundProducts = 0;
        for (WebElement el : products) {
            if (el.getText().equals(productName)) {
                foundProducts++;
            }
        }
        assertEquals( 1, foundProducts);
    }
}
