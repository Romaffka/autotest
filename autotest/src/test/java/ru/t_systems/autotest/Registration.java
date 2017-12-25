package ru.t_systems.autotest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class Registration {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "table a")
    private WebElement newCustomerLink;

    @FindBy(css = "[name='firstname']")
    private WebElement firstName;

    @FindBy(css = "[name='lastname']")
    private WebElement lastName;

    @FindBy(css = "[name='address1']")
    private WebElement address;

    @FindBy(css = "[name='postcode']")
    private WebElement postCode;

    @FindBy(css = "[name='city']")
    private WebElement city;

    @FindBy(className = "select2-selection")
    private WebElement country;

    @FindBy(className = "select2-search__field")
    private WebElement inputCountry;

    @FindBy(css = "[name='email']")
    private WebElement email;

    @FindBy(css = "[name='phone']")
    private WebElement phone;

    @FindBy(css = "[name='password']")
    private WebElement password;

    @FindBy(css = "[name='confirmed_password']")
    private WebElement confirmedPassword;

    @FindBy(css = "[name='create_account']")
    private WebElement createAccountButton;

    @FindBy(css = "#box-account li:last-child a")
    private WebElement logout;

    @FindBy(css = "[name='login']")
    private WebElement login;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost:9080/litecart");
        PageFactory.initElements(driver, this);
    }

    @Test
    public void createNewAccount() {
        Random rnd = new Random();
        String emailTest = rnd.nextInt() + "@site.com";

        newCustomerLink.click();
        firstName.sendKeys("Zanzibar");
        lastName.sendKeys("Zanzibarov");
        address.sendKeys("Vjazov street 1");
        postCode.sendKeys("85931");
        city.sendKeys("Piter");
        country.click();
        inputCountry.sendKeys("United States" + Keys.ENTER);
        email.sendKeys(emailTest);
        phone.sendKeys("5555555");
        password.sendKeys("123456");
        confirmedPassword.sendKeys("123456");
        createAccountButton.click();

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#box-account li:last-child a")));

        logout.click();

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table a")));

        email.sendKeys(emailTest);
        password.sendKeys("123456");
        login.click();

        (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#box-account li:last-child a")));

        logout.click();
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
