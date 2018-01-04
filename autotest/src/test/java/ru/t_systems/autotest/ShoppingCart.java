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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class ShoppingCart {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = ".product")
    private WebElement firstProduct;

    @FindBy(css = "[name='add_cart_product']")
    private WebElement addProduct;

    @FindBy(css = "[title='My Store']")
    private WebElement mainPageLink;

    @FindBy(css = "[name='options[Size]']")
    private List<WebElement> options;

    @FindBy(css = "#cart .link")
    private WebElement checkout;

    @FindBy(css = "[name='remove_cart_item']")
    private WebElement removeButton;

    @FindBy(css = "[name='confirm_order']")
    private List<WebElement> confirmButton;

    @FindBy(css = ".quantity")
    private WebElement quantity;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("http://localhost:9080/litecart/en/");
        PageFactory.initElements(driver, this);
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

    @Test
    public void AddAndDeleteProduct() {
        for (int i = 0; i < 3; i++) {
            firstProduct.click();
            if (options.size() > 0) {
                Select select = new Select(options.get(0));
                select.selectByIndex(1);
            }

            String count = quantity.getText();

            addProduct.click();

            wait.until((d) -> !quantity.getText().equals(count));

            mainPageLink.click();
        }

        checkout.click();

        while (true) {
            List<WebElement> tr = driver.findElements(By.cssSelector("#box-checkout-summary tr"));
            removeButton.click();
            if (tr.size() > 0) {
                wait.until(stalenessOf(tr.get(0)));
            }
            if (confirmButton.size() == 0) {
                break;
            }
        }
    }
}
