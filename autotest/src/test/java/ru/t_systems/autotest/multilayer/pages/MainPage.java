package ru.t_systems.autotest.multilayer.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends Page {
    @FindBy(css = ".product")
    private WebElement firstProduct;

    @FindBy(css = "#cart .link")
    private WebElement checkout;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage openMainPage() {
        driver.get("http://localhost:9080/litecart/en/");
        return this;
    }

    public MainPage goToProductPage() {
        firstProduct.click();
        return this;
    }

    public MainPage goToShoppingCartPage() {
        checkout.click();
        return this;
    }
}
